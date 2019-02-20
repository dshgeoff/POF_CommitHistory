package com.pof_commithistory.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CommitList extends BaseEntity implements Parcelable {

    @SerializedName("commit")
    public Commit commit;

    @SerializedName("committer")
    public Committer committer;

    @SerializedName("sha")
    public String sha = "";


    public static class Commit extends BaseEntity implements Parcelable {

        @SerializedName("committer")
        public Committer committer;

        @SerializedName("message")
        public String message = "";


        protected Commit(Parcel in) {
            committer = in.readParcelable(Committer.class.getClassLoader());
            message = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(committer, flags);
            dest.writeString(message);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Commit> CREATOR = new Creator<Commit>() {
            @Override
            public Commit createFromParcel(Parcel in) {
                return new Commit(in);
            }

            @Override
            public Commit[] newArray(int size) {
                return new Commit[size];
            }
        };
    }

    public static class Committer extends BaseEntity implements Parcelable {

        @SerializedName("date")
        public String date = "";

        @SerializedName("login")
        public String login = "";

        @SerializedName("avatar_url")
        public String avatar_url = "";

        @SerializedName("html_url")
        public String html_url = "";

        protected Committer(Parcel in) {
            date = in.readString();
            login = in.readString();
            avatar_url = in.readString();
            html_url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(login);
            dest.writeString(avatar_url);
            dest.writeString(html_url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Committer> CREATOR = new Creator<Committer>() {
            @Override
            public Committer createFromParcel(Parcel in) {
                return new Committer(in);
            }

            @Override
            public Committer[] newArray(int size) {
                return new Committer[size];
            }
        };
    }

    protected CommitList(Parcel in) {
        commit = in.readParcelable(Commit.class.getClassLoader());
        committer = in.readParcelable(Committer.class.getClassLoader());
        sha = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(commit, flags);
        dest.writeParcelable(committer, flags);
        dest.writeString(sha);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommitList> CREATOR = new Creator<CommitList>() {
        @Override
        public CommitList createFromParcel(Parcel in) {
            return new CommitList(in);
        }

        @Override
        public CommitList[] newArray(int size) {
            return new CommitList[size];
        }
    };
}
