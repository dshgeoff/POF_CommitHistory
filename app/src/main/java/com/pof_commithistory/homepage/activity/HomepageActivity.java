package com.pof_commithistory.homepage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pof_commithistory.R;
import com.pof_commithistory.general.activity.BaseActivity;
import com.pof_commithistory.general.presenter.FragmentPresenter;
import com.pof_commithistory.homepage.fragment.HomepageFragment;
import com.pof_commithistory.homepage.presenter.HomepagePresenter;

public class HomepageActivity extends BaseActivity {

    @Override
    protected int contentView() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomepageFragment homepageFragment = new HomepageFragment();
        new HomepagePresenter(this, homepageFragment);
        new FragmentPresenter(this).push(homepageFragment).asMajorContent();
    }
}
