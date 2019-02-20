package com.pof_commithistory.homepage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.pof_commithistory.R;
import com.pof_commithistory.data.entity.CommitList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.ViewHolder> {

    private final List<CommitList> mList = new ArrayList<>();
    private IRecyclerOnItemClickListener mRecyclerOnItemClickListener;
    private Integer previousSelected;

    @NonNull
    @Override
    public CommitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CommitListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_commit_list_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CommitListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setupView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(CommitList[] commitList) {
        if (commitList == null) return;
        this.mList.clear();
        this.mList.addAll(Arrays.asList(commitList));
        notifyDataSetChanged();
    }

    public boolean isLastItem(int position) {
        return position == mList.size() - 1;
    }

    public void unselectItem(int position) {
        previousSelected = null;
        notifyItemChanged(position);
    }

    private class CommitListViewHolder extends ViewHolder {
        View itemView;
        LinearLayout llCommitContainer;
        ImageView ivAvatar;
        TextView tvMessage, tvDate, tvLogin;
        ViewFlipper vfDateLogin;

        public CommitListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(View view) {
            this.itemView = view;
            llCommitContainer = (LinearLayout) itemView.findViewById(R.id.llCommitContainer);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            vfDateLogin = (ViewFlipper) itemView.findViewById(R.id.vfDateLogin);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvLogin = (TextView) itemView.findViewById(R.id.tvLogin);
        }

        @Override
        protected void setupView(final int position) {
            if (mList.get(position) == null) {
                return;
            }
            if (previousSelected != null && position == previousSelected) {
                llCommitContainer.setSelected(true);
            } else {
                llCommitContainer.setSelected(false);
            }
            Glide.with(itemView.getContext()).load(mList.get(position).committer.avatar_url).into(ivAvatar);
            tvMessage.setText(mList.get(position).commit.message);
            tvDate.setText(mList.get(position).commit.committer.date);
            tvLogin.setText(mList.get(position).committer.login);

            if ((position % 2) == 0) {
                vfDateLogin.startFlipping();
            } else {
                vfDateLogin.stopFlipping();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerOnItemClickListener != null) {
                        llCommitContainer.setSelected(true);

                        if (previousSelected == null) {
                            previousSelected = getAdapterPosition();
                        }

                        if (previousSelected != null && getAdapterPosition() != previousSelected) {
                            notifyItemChanged(previousSelected);
                            previousSelected = getAdapterPosition();
                        }

                        mRecyclerOnItemClickListener.onItemClick(getAdapterPosition(), mList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            bindView(itemView);
        }

        protected abstract void bindView(View view);

        protected abstract void setupView(int position);
    }

    public void setRecyclerItemClickListener(IRecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.mRecyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    public interface IRecyclerOnItemClickListener {
        void onItemClick(int position, CommitList commit);
    }
}
