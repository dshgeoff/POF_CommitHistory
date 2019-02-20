package com.pof_commithistory.data;

import android.support.annotation.NonNull;

import com.pof_commithistory.data.interfaces.IHomepageDataSource;

public class HomepageRepository implements IHomepageDataSource {

    private static HomepageRepository INSTANCE = null;

    private final IHomepageDataSource mHomepageDataSource;

    private HomepageRepository(IHomepageDataSource homepageDataSource) {
        mHomepageDataSource = homepageDataSource;
    }

    public static HomepageRepository getInstance(IHomepageDataSource pofDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new HomepageRepository(pofDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void retrieveCommitList(@NonNull RetrieveCommitListCallback callback) {
        mHomepageDataSource.retrieveCommitList(callback);
    }
}
