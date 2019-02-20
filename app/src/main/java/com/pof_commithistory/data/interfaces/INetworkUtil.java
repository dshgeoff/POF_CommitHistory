package com.pof_commithistory.data.interfaces;

import com.pof_commithistory.data.StringCallback;

import java.util.Map;

public interface INetworkUtil {
    /**
     * Default charset for JSON requestJson.
     */
    String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for requestJson.
     */
    String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    String PROTOCOL_CONTENT_TYPE_JSON =
            String.format("application/json");

    void requestString(int method,
                       Map<String, String> header,
                       Map<String, String> params,
                       byte[] body,
                       String bodyContentType,
                       StringCallback callback,
                       String tag);

    void cancel(String tag);
}
