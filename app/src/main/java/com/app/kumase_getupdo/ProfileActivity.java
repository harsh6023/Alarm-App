package com.app.kumase_getupdo;

import static com.android.billingclient.api.BillingClient.SkuType.SUBS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.app.kumase_getupdo.adapter.SubscriptionAdapter;
import com.app.kumase_getupdo.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.api.RetrofitClient;
import com.jbs.general.model.response.alarms.AlarmsApiData;
import com.jbs.general.model.response.singup.MainResponseSignUp;
import com.jbs.general.model.response.singup.SignUpData;
import com.jbs.general.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProfileActivity extends BaseAppActivity<ProfileActivity> implements RecycleViewInterface{

    private ActivityProfileBinding binding;
    private BillingClient billingClient;
    private List<ProductDetails> productDetailsList;
    private Handler handler;
    private SubscriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        productDetailsList = new ArrayList<>();
        handler = new Handler();

        SignUpData signUpData = new Gson().fromJson(preferenceUtils.getString(Constants.PreferenceKeys.USER_DATA), SignUpData.class);
        binding.tvFullName.setText(signUpData.getFull_name());
        binding.tvEmail.setText(signUpData.getEmail());

        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(
                        (billingResult, list) -> {
                            Log.e("Billing", new Gson().toJson(billingResult) + " **\n" + new Gson().toJson(list));
                            if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK && list !=null) {
                                for (Purchase purchase: list){
                                    verifySubPurchase(purchase);
                                }
                            }
                        }
                ).build();

        //start the connection after initializing the billing client
        establishConnection();

        binding.mbRestorePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restorePurchases();
            }
        });

        binding.mbLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogout(ProfileActivity.this, LoginActivity.class);
            }
        });
    }

    void establishConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.e("BillingResult", billingResult.getResponseCode() + "\n **");
                    showProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("onBillingServiceDisconnected", "hereeee...!");
                establishConnection();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void showProducts() {
        ImmutableList<QueryProductDetailsParams.Product> productList = ImmutableList.of(
                //Product 1
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("test_monthly_2")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()

                /*//Product 2
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("test_sub_monthly1")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()*/

        );

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        Log.e("BillProductList", new Gson().toJson(productList) + "\n **");

        billingClient.queryProductDetailsAsync(
                params,
                (billingResult, prodDetailsList) -> {
                    // Process the result
                    Log.e("Billing", new Gson().toJson(billingResult) + " **\n" + new Gson().toJson(prodDetailsList));
                    productDetailsList.clear();
                    handler.postDelayed(() -> {
                        binding.loadProducts.setVisibility(View.INVISIBLE);
                        productDetailsList.addAll(prodDetailsList);
                        adapter = new SubscriptionAdapter(getApplicationContext(), productDetailsList, ProfileActivity.this);
                        binding.recyclerview.setHasFixedSize(true);
                        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.recyclerview.setAdapter(adapter);

                    },2000);

                }
        );

    }

    void launchPurchaseFlow(ProductDetails productDetails) {
        assert productDetails.getSubscriptionOfferDetails() != null;
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(productDetails.getSubscriptionOfferDetails().get(0).getOfferToken())
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();

        billingClient.launchBillingFlow(ProfileActivity.this, billingFlowParams);
    }

    void verifySubPurchase(Purchase purchases) {

        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();

        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //use prefs to set premium
                //Setting premium to 1
                // 1 - premium
                // 0 - no premium
                preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1);
                goBack();
            }
        });
    }

    private void goBack() {
        RetrofitClient.getInstance().getApi().Subscribe(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), parseDateToyyyyMMddhhmmss()).enqueue(new Callback<MainResponseSignUp>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseSignUp> call, @NonNull Response<MainResponseSignUp> response) {
                if (response.body() == null) {
                    showSnackbarShort(getString(R.string.error_something_went_wrong));
                    return;
                }

                MainResponseSignUp mainResponseSignUp = response.body();
                if (mainResponseSignUp.isSuccess()){
                    preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1);

                }else {
                    showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseSignUp.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseSignUp> call, @NonNull Throwable t) {
                hideLoader();
                Timber.tag("onFailure?Subscribe").e(t);
            }
        });

        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        },2000);
    }

    protected void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifySubPurchase(purchase);
                            }
                        }
                    }
                }
        );
    }

    void restorePurchases(){
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener((billingResult, list) -> {}).build();
        final BillingClient finalBillingClient = billingClient;
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    finalBillingClient.queryPurchasesAsync(
                            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (billingResult1, list) -> {
                                if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK){
                                    if(list.size()>0){
                                        if (list.get(0).isAutoRenewing()) {
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1);
                                            // set 1 to activate premium feature
                                            showSnackbarShort("Successfully restored");
                                            goBack();
                                        }else {
                                            showSnackbarShort("Oops, No purchase found.");
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                        }
                                    }else {
                                        showSnackbarShort("Oops, No purchase found.");
                                        preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(int pos) {
        launchPurchaseFlow(productDetailsList.get(pos));
    }
}