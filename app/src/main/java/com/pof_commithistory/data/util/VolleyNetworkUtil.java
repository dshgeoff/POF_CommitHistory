package com.pof_commithistory.data.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pof_commithistory.data.CustomUrlStack;
import com.pof_commithistory.data.StringCallback;
import com.pof_commithistory.data.StringRequest;
import com.pof_commithistory.data.interfaces.INetworkUtil;
import com.pof_commithistory.data.interfaces.IPOFBaseCallback;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VolleyNetworkUtil implements INetworkUtil {

    private final static int DEFAULT_TIMEOUT = 40 * 1000;
    private final static int MAX_RETRIES = 0;
    private final static int BACKOFF_MULT = 0;

    private static VolleyNetworkUtil mInstance;

    private final RequestQueue mRequestQueue;
    private final List<OnRequestFinishListener> mOnRequestFinishListeners;

    public static VolleyNetworkUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyNetworkUtil(context);
        }

        return mInstance;
    }

    private VolleyNetworkUtil(Context context) {
        mOnRequestFinishListeners = new ArrayList<>();
        CookieHandler.setDefault(new CookieManager());
        mRequestQueue = Volley.newRequestQueue(context, new CustomUrlStack());
        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                synchronized (mOnRequestFinishListeners) {
                    Iterator<OnRequestFinishListener> iterator = mOnRequestFinishListeners.iterator();
                    while (iterator.hasNext()) {
                        OnRequestFinishListener listener = iterator.next();
                        if (listener.getRequest() == request) {
                            if (request.isCanceled()) {
                                listener.onRequestFinished();
                            }
                            iterator.remove();
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void requestString(int method, Map<String, String> header, Map<String, String> params, byte[] body, String bodyContentType, StringCallback callback, String tag) {
        cancel(tag);

        String url = "https://api.github.com/repos/definitelytyped/definitelytyped/commits";

        StringBuilder screenLogStringBuilder = new StringBuilder();
        Log.d("VolleyNetworkUtil", "==================================Start Request==================================");
        screenLogStringBuilder.append("==================================Start Request==================================\n");
        if (method == Request.Method.GET) {
            url = appendGETParams(url, params);
        }
        Log.d("VolleyNetworkUtil", "url >>> " + url);
        screenLogStringBuilder.append(String.format("Request URL: \n%s\n", url));
        String methodString = "";
        if (method == Request.Method.GET) {
            methodString = "GET";
        } else if (method == Request.Method.POST) {
            methodString = "POST";
        } else if (method == Request.Method.PUT) {
            methodString = "PUT";
        } else if (method == Request.Method.DELETE) {
            methodString = "DELETE";
        }
        screenLogStringBuilder.append(String.format("Method: \n%s\n\n", methodString));

        if (header != null) {
            Log.d("VolleyNetworkUtil", "headers >>> " + header.toString());
            screenLogStringBuilder.append(String.format("Headers: \n%s\n", header));
        }
        if (body != null) {
            try {
                String bodyString = new String(body, "UTF-8");
                Log.d("VolleyNetworkUtil", "body >>> " + bodyString);
                screenLogStringBuilder.append(String.format("Body: \n%s\n", bodyString));
            } catch (Exception e) {
            }
        }
        if (params != null) {
            Log.d("VolleyNetworkUtil", "params >>> " + params.toString());
            screenLogStringBuilder.append(String.format("Payload: \n%s\n", params));
        }
        Log.d("VolleyNetworkUtil", "==================================End Request==================================");
        screenLogStringBuilder.append("==================================End Request==================================");
        Log.d("VolleyNetworkUtil", screenLogStringBuilder.toString());

        StringRequest request = null;
        try {
            request = StringRequest.getInstance(StringRequest.class, method,
                    url,
                    params,
                    header,
                    body,
                    bodyContentType,
                    callback,
                    callback,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_TIMEOUT,
                MAX_RETRIES,
                BACKOFF_MULT));
        request.setTag(tag);
        request.setShouldCache(false);
        synchronized (mOnRequestFinishListeners) {
            mOnRequestFinishListeners.add(new OnRequestFinishListener(callback, request));
        }
        mRequestQueue.add(request);
    }

    private String appendGETParams(String url, Map<String, String> params) {
        if (params != null) {
            String paramsStr = encodeParameters(params, "UTF-8");
            url += "?" + paramsStr;
        }
        return url;
    }

    private String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public void cancel(final String tag) {
        synchronized (mRequestQueue) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    if (request.getTag() == null || TextUtils.isEmpty(tag)) return false;

                    return request.getTag().toString().equals(tag);
                }
            });
        }
    }

    private class OnRequestFinishListener {
        private IPOFBaseCallback mCallback;
        private Request mRequest;

        OnRequestFinishListener(IPOFBaseCallback callback, Request request) {
            mCallback = callback;
            mRequest = request;
        }

        void onRequestFinished() {
            mCallback.onCancelled();
        }

        Request getRequest() {
            return mRequest;
        }
    }
}
