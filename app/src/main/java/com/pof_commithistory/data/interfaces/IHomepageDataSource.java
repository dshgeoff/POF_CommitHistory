package com.pof_commithistory.data.interfaces;

import android.support.annotation.NonNull;

import com.pof_commithistory.data.entity.CommitList;

public interface IHomepageDataSource extends IBaseDataSource {

    void retrieveCommitList(@NonNull RetrieveCommitListCallback callback);

    interface RetrieveCommitListCallback extends IBaseDataSource.BaseCallback {
        void onRetrieveCommitListSuccess(CommitList[] commitList);
    }

}
