package com.app.kumase_getupdo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.ProductDetails;
import com.app.kumase_getupdo.R;
import com.app.kumase_getupdo.RecycleViewInterface;
import com.google.gson.Gson;
import com.jbs.general.model.response.alarms.AlarmsApiData;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.myViewHolder> {

    private Context context;
    private List<ProductDetails> productDetailsList;
    private RecycleViewInterface recycleViewInterface;

    public SubscriptionAdapter(Context context, List<ProductDetails> productDetailsList, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.productDetailsList = productDetailsList;
        this.recycleViewInterface = recycleViewInterface;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        ProductDetails productDetails = productDetailsList.get(position);
        List<ProductDetails.SubscriptionOfferDetails> subDetails = productDetails.getSubscriptionOfferDetails();

        Log.e("SubDetails", new Gson().toJson(subDetails) + " **");
        holder.product_name.setText(subDetails.get(position).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice() + " / Monthly");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleViewInterface.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name;
        public myViewHolder(@NonNull View view) {
            super(view);
            product_name = view.findViewById(R.id.product_name);

        }
    }
}
