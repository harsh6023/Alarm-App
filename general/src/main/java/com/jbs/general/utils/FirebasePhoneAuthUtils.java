package com.jbs.general.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirebasePhoneAuthUtils {

    //region #Variables
    private final Context context;
    private FirebaseAuth firebaseAuth;

    private String verificationId;
    //endregion

    //region #Constructor
    @Inject
    FirebasePhoneAuthUtils(Context context) {
        this.context = context;
    }
    //endregion

    //region #Getter-Setter
    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }
    //endregion

    /**
     * Initialize Firebase Phone Number Authentication
     */
    public void initializeFirebasePhoneNumberAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Send Verification Code to the number
     *
     * @param activity    - Activity
     * @param countryCode - Country Code
     * @param mobile      - Mobile Number
     */
    public void sendVerificationCode(Activity activity, String countryCode, String mobile, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(countryCode + mobile)
                .setTimeout(Constants.Delays.PHONE_AUTH_TIMEOUT, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationStateChangedCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    /**
     * Verify Verification Code
     *
     * @param activity           - Activity
     * @param code               - Code
     * @param onCompleteListener - Complete Listener
     */
    public void verifyVerificationCode(Activity activity, String code, OnCompleteListener<AuthResult> onCompleteListener) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, onCompleteListener);
    }
}
