package com.pof_commithistory.data.interfaces;

public interface IBaseDataSource {

    interface BaseCallback {

        void onCancelled();

        void onError();

        void onRespond();
    }
}
