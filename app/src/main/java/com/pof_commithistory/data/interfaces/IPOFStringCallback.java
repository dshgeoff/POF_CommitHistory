package com.pof_commithistory.data.interfaces;

import com.android.volley.Response;

public interface IPOFStringCallback<RAWRESPONSE, RESPONSE, ERROR extends IPOFErrorResponse> extends IPOFBaseCallback<RESPONSE>, Response.Listener<RAWRESPONSE>, Response.ErrorListener {

    String[] getErrorKey();

    String[] getSpecialErrorCode();

    IPOFObjectParser getApiObjectParser();

    Class<RESPONSE> getSuccessClazz();

    Class<ERROR> getErrorClazz();
}
