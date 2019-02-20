package com.pof_commithistory.homepage.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pof_commithistory.data.HomepageRepository;
import com.pof_commithistory.data.entity.CommitList;
import com.pof_commithistory.data.interfaces.IHomepageDataSource;
import com.pof_commithistory.data.remote.HomepageDataSource;
import com.pof_commithistory.general.presenter.BasePresenter;
import com.pof_commithistory.homepage.interfaces.IHomepageContract;

public class HomepagePresenter<V extends IHomepageContract.View> extends BasePresenter<V> implements IHomepageContract.Presenter {

    protected transient HomepageRepository mHomepageRepository;

    public HomepagePresenter(@NonNull Context context, @NonNull V view) {
        super(context, view);
    }

    @Override
    public void init() {
        mHomepageRepository = HomepageRepository.getInstance(new HomepageDataSource(getContext()));
    }

    @Override
    public void requestCommitList() {
        getView().setLoadingIndicator(true);
        mHomepageRepository.retrieveCommitList(new IHomepageDataSource.RetrieveCommitListCallback() {
            @Override
            public void onRetrieveCommitListSuccess(CommitList[] commitList) {
                if (commitList != null && commitList.length > 0) {
                    getView().setEmptyContainer(false);
                    getView().updateCommitList(commitList);
                } else {
                    getView().setEmptyContainer(true);
                }
            }

            @Override
            public void onCancelled() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onRespond() {
                getView().setLoadingIndicator(false);
                getView().setSwipeRefreshing(false);
            }
        });
    }
}
