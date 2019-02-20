package com.pof_commithistory.general.interfaces;

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    boolean isActive();

    void setLoadingIndicator(boolean active);

}
