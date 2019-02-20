package com.pof_commithistory.homepage.interfaces;

import com.pof_commithistory.data.entity.CommitList;
import com.pof_commithistory.general.interfaces.IBasePresenter;
import com.pof_commithistory.general.interfaces.IBaseView;

public interface IHomepageContract {

    interface View extends IBaseView<Presenter> {

        void setSwipeRefreshing(boolean isRefresh);

        void setEmptyContainer(boolean isEmpty);

        void updateCommitList(CommitList[] commitList);

    }

    interface Presenter extends IBasePresenter {

        void requestCommitList();

    }
}
