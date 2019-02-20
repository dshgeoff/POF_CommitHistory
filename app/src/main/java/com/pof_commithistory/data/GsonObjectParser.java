package com.pof_commithistory.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pof_commithistory.data.interfaces.IPOFObjectParser;

public class GsonObjectParser implements IPOFObjectParser {

    @Override
    public <RESPONSE> RESPONSE parseObject(String json, Class<RESPONSE> responseClass) {
        try {
            return new Gson().fromJson(json, responseClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    @Override
    public String toJsonString(Object object) {
        try {
            return new Gson().toJson(object);
        } catch (Exception e) {
            return null;
        }
    }
}
