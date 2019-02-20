package com.pof_commithistory.homepage.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pof_commithistory.R;
import com.pof_commithistory.data.entity.CommitList;
import com.pof_commithistory.general.fragment.BaseFragment;
import com.pof_commithistory.general.interfaces.IBasePresenter;
import com.pof_commithistory.general.view.PagingRecyclerView;
import com.pof_commithistory.homepage.adapter.CommitListAdapter;
import com.pof_commithistory.homepage.interfaces.IHomepageContract;

import static com.pof_commithistory.CommitHistoryConstants.COMMIT;
import static com.pof_commithistory.CommitHistoryConstants.COMMITPOSITION;

public class HomepageFragment extends BaseFragment implements IHomepageContract.View {

    private IHomepageContract.Presenter mPresenter;
    private CommitListAdapter mAdapter;

    private PagingRecyclerView rvCommitList;
    private SwipeRefreshLayout swlCommitList;
    private LinearLayout vEmpty;

    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void bindView(View view) {
        rvCommitList = (PagingRecyclerView) view.findViewById(R.id.rvCommitList);
        swlCommitList = (SwipeRefreshLayout) view.findViewById(R.id.swlCommitList);
        vEmpty = (LinearLayout) view.findViewById(R.id.vEmpty);
    }

    @Override
    public void setupView(@Nullable Bundle savedInstanceState) {
        rvCommitList.setSwipeRefreshLayout(swlCommitList);

        if (rvCommitList.getLayoutManager() == null) {
            rvCommitList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCommitList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int padding = (int) getResources().getDimension(R.dimen.general_margin_5px);
                    int sideMargin = (int) getResources().getDimension(R.dimen.general_margin_15px);
                    int position = parent.getChildAdapterPosition(view);

                    boolean isLastItem = mAdapter.isLastItem(position);
                    outRect.set(sideMargin, position == 0 ? padding : 0, sideMargin, isLastItem ? 0 : padding);
                }
            });
        }

        if (rvCommitList.getAdapter() == null) {
            rvCommitList.setAdapter(getAdapter());
        }

        swlCommitList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestCommitList();
            }
        });

        rvCommitList.setPagingListener(new PagingRecyclerView.PagingListener() {
            @Override
            public void onLoadNextPage() {

            }
        });

        mPresenter.requestCommitList();
    }

    @Override
    public IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(IHomepageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private CommitListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new CommitListAdapter();
            mAdapter.setRecyclerItemClickListener(new CommitListAdapter.IRecyclerOnItemClickListener() {
                @Override
                public void onItemClick(int position, CommitList commit) {
                    DetailFragment detailFragment = DetailFragment.getInstance();
                    if (getFragmentManager() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(COMMITPOSITION, position);
                        bundle.putParcelable(COMMIT, commit);
                        detailFragment.setArguments(bundle);
                        detailFragment.setCloseClickListener(new DetailFragment.ICloseClickListener() {
                            @Override
                            public void onCloseClick(int position) {
                                mAdapter.unselectItem(position);
                            }
                        });
                        detailFragment.show(getFragmentManager(), DetailFragment.Tag);
                    }
                }
            });
        }

        return mAdapter;
    }

    @Override
    public void setSwipeRefreshing(boolean isRefresh) {
        swlCommitList.setRefreshing(isRefresh);
    }

    @Override
    public void setEmptyContainer(boolean isEmpty) {
        if (isEmpty) {
            vEmpty.setVisibility(View.VISIBLE);
            rvCommitList.setVisibility(View.GONE);
        } else {
            vEmpty.setVisibility(View.GONE);
            rvCommitList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateCommitList(CommitList[] commitList) {
        mAdapter.updateList(commitList);
    }
}
