package com.pof_commithistory.homepage.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pof_commithistory.R;
import com.pof_commithistory.data.entity.CommitList;

import static com.pof_commithistory.CommitHistoryConstants.COMMIT;
import static com.pof_commithistory.CommitHistoryConstants.COMMITPOSITION;

public class DetailFragment extends BottomSheetDialogFragment {

    private TextView tvMessage, tvDate, tvLogin, tvSha;
    private ImageView ivAvatar, ivClose;

    ICloseClickListener mCloseClickListener;

    public static String Tag = "DetailFragment";
    private int mCommitPosition;
    private CommitList mCommit;

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvLogin = (TextView) view.findViewById(R.id.tvLogin);
        tvSha = (TextView) view.findViewById(R.id.tvSha);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        ivClose = (ImageView) view.findViewById(R.id.ivClose);

        /* Set peek initial peek height of 250dp if necessary */
//        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
//                bottomSheetBehavior.setPeekHeight((int) (getResources().getDimension(R.dimen.general_margin_250px) / getResources().getDisplayMetrics().density));
//            }
//        });

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mCommitPosition = bundle.getInt(COMMITPOSITION);
            mCommit = bundle.getParcelable(COMMIT);

            Glide.with(view.getContext()).load(mCommit.committer.avatar_url).into(ivAvatar);
            tvDate.setText(mCommit.commit.committer.date);
            tvLogin.setText(mCommit.committer.login);
            tvSha.setText(mCommit.sha);
            tvMessage.setText(mCommit.commit.message);

            ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mCommit.committer.html_url));
                    startActivity(browserIntent);
                }
            });

            if (mCloseClickListener != null) {
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        mCloseClickListener.onCloseClick(mCommitPosition);
                    }
                });
            }
        }

        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCloseClickListener != null && getArguments() != null) {
            mCloseClickListener.onCloseClick(mCommitPosition);
        }
    }

    public void setCloseClickListener(ICloseClickListener closeClickListener) {
        this.mCloseClickListener = closeClickListener;
    }

    public interface ICloseClickListener {
        void onCloseClick(int position);
    }
}
