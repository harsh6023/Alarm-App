package com.jbs.general.listeners;

public abstract class ConfirmationAlertDialogClickListener {

    /**
     * Positive Button Click
     */
    public abstract void onPositiveButtonClick();

    /**
     * Negative Button Click
     */
    public void onNegativeButtonClick() {
        //override only if necessary
    }
}