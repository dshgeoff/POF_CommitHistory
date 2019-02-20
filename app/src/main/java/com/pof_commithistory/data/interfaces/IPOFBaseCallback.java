package com.pof_commithistory.data.interfaces;

public interface IPOFBaseCallback<RESPONSE> {

    void onCancelled();

    void onRespond();

    void onSuccess(RESPONSE response);

    void onError();
}
