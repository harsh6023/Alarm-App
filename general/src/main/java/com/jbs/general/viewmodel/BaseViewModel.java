package com.jbs.general.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jbs.general.General;
import com.jbs.general.utils.PreferenceUtils;

public class BaseViewModel extends ViewModel {

    protected PreferenceUtils preferenceUtils = General.getInstance().getAppComponent().providePreferenceUtils();
}
