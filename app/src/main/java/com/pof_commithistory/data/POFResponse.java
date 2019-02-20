package com.pof_commithistory.data;

import android.text.TextUtils;

import java.util.Map;

public class POFResponse {
    public Map<String, String> header;
    public String json;
    public POFMessage pofMessage;

    public void setPOFError(String errorString) {
        if (TextUtils.isEmpty(errorString)) {
            return;
        }
        pofMessage = new POFMessage();
        try {
            pofMessage.pofError = POFMessageError.valueOf(errorString);
        } catch (Exception e) {
            pofMessage.pofError = POFMessageError.UN;
        }
    }

    public static class POFMessage {
        public POFMessageError pofError;
    }

    public enum POFMessageError {
        UN("unexpected_error");

        private String mErrorString;

        POFMessageError(String errorString) {
            mErrorString = errorString;
        }

        public String toString() {
            return mErrorString;
        }
    }
}
