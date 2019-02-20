package com.pof_commithistory.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class BaseDataSource {

    private Context mContext;
    private Gson mGson;

    protected BaseDataSource(@NonNull Context context) {
        mContext = context;
        mGson = new Gson();
    }

    protected Context getContext() {
        return mContext;
    }

    protected Gson getGson() {
        return mGson;
    }

}
