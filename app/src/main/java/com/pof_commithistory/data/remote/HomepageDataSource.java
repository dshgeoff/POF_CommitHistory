package com.pof_commithistory.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.pof_commithistory.data.APIAction;
import com.pof_commithistory.data.APIManager;
import com.pof_commithistory.data.ErrorResponse;
import com.pof_commithistory.data.StringCallbackHandler;
import com.pof_commithistory.data.entity.CommitList;
import com.pof_commithistory.data.interfaces.IHomepageDataSource;

public class HomepageDataSource extends BaseDataSource implements IHomepageDataSource {

    public HomepageDataSource(@NonNull Context context) {
        super(context);
    }

    @Override
    public void retrieveCommitList(@NonNull final RetrieveCommitListCallback callback) {
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.GET,
                null,
                null,
                null,
                null,
                new StringCallbackHandler<CommitList[], ErrorResponse>(CommitList[].class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {
                        callback.onCancelled();
                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(CommitList[] commitList) {
                        callback.onRetrieveCommitListSuccess(commitList);
                    }

                    @Override
                    public void onError() {
                        callback.onError();
                    }

                }, APIAction.RequestCommitHistory.toString()
        );
    }
}
