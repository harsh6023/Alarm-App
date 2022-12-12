package com.jbs.general.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.jbs.general.R;
import com.jbs.general.databinding.DialogCommonBinding;
import com.jbs.general.listeners.ConfirmationAlertDialogClickListener;
import com.jbs.general.widget.GeneralAppCompatTextView;

public class ConfirmationAlertDialog {

    private static AlertDialog confirmationDialog;

    public static void showConfirmationDialog(Activity activity, boolean isCancelable, String title, String message,
                                              String positiveButtonText, String negativeButtonText,
                                              ConfirmationAlertDialogClickListener confirmationAlertDialogClickListener) {

        //validation if dialog is null or already open
        if (confirmationDialog != null && confirmationDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        confirmationDialog = dialogBuilder.create();
        confirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmationDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmationDialog.setCancelable(isCancelable);

        if (!TextUtils.isEmpty(title)) {
            confirmationDialog.setTitle(title);
        }
        confirmationDialog.setMessage(message);

        if (!TextUtils.isEmpty(positiveButtonText)) {
            confirmationDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonText,
                    (dialog, id) -> {
                        confirmationDialog.dismiss();
                        confirmationAlertDialogClickListener.onPositiveButtonClick();
                    });
        }

        if (!TextUtils.isEmpty(negativeButtonText)) {
            confirmationDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeButtonText,
                    (dialog, id) -> {
                        confirmationDialog.dismiss();
                        confirmationAlertDialogClickListener.onNegativeButtonClick();
                    });
        }

        confirmationDialog.show();

        /*AlertDialog.Builder builder = new AlertDialog.Builder(activity, com.jbs.general.R.style.AlertDialogTheme);
        builder.setCancelable(isCancelable);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_common, null, false);
        AppCompatImageView ivClose = view.findViewById(R.id.iv_Close);
        GeneralAppCompatTextView tvTitle = view.findViewById(R.id.tv_title);
        GeneralAppCompatTextView tvDesc = view.findViewById(R.id.tvDesc);
        GeneralAppCompatTextView tvNegative = view.findViewById(R.id.tv_negative);
        GeneralAppCompatTextView tvPositive = view.findViewById(R.id.tv_positive);

        tvTitle.setText(title);
        tvDesc.setText(message);
        tvNegative.setVisibility(View.GONE);
        tvPositive.setVisibility(View.GONE);
        ivClose.setOnClickListener(v -> confirmationDialog.dismiss());

        if (!TextUtils.isEmpty(positiveButtonText)) {
            tvPositive.setText(positiveButtonText);
            tvPositive.setVisibility(View.VISIBLE);
            tvPositive.setOnClickListener(v -> {
                confirmationDialog.dismiss();
                confirmationAlertDialogClickListener.onPositiveButtonClick();
            });
        }

        if (!TextUtils.isEmpty(negativeButtonText)) {
            tvNegative.setText(negativeButtonText);
            tvNegative.setVisibility(View.VISIBLE);
            tvNegative.setOnClickListener(v -> {
                confirmationDialog.dismiss();
                confirmationAlertDialogClickListener.onNegativeButtonClick();
            });
        }

        builder.setView(view);
        confirmationDialog = builder.create();
        confirmationDialog.show();*/
    }

    /**
     * Dismiss Confirmation Dialog
     */
    public static void dismissConfirmationDialog() {
        if (confirmationDialog != null && confirmationDialog.isShowing()) {
            confirmationDialog.dismiss();
        }
    }
}