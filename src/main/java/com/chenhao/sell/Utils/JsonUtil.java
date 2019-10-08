package com.chenhao.sell.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil
{
    /**json格式化工具*/
    public static String toJson(Object object)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(object);
        return json;
    }
}
