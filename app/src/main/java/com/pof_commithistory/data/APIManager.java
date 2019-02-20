package com.pof_commithistory.data;

import android.content.Context;

import com.pof_commithistory.data.interfaces.INetworkUtil;
import com.pof_commithistory.data.util.VolleyNetworkUtil;

public class APIManager {
    public static INetworkUtil getNetworkUtil(Context context) {
        return VolleyNetworkUtil.getInstance(context);
    }
}
