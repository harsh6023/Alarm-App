package com.jbs.general.dagger;

import com.jbs.general.activity.BaseActivity;
import com.jbs.general.fragment.BaseFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = AppComponent.class, modules = ViewModule.class)
public interface ViewComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

}
