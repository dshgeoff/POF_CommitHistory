package com.pof_commithistory.data.interfaces;

import java.io.Serializable;

public interface IPOFObjectParser extends Serializable {

    <RESPONSE> RESPONSE parseObject(String json, Class<RESPONSE> responseClass);

    String toJsonString(Object object);
}
