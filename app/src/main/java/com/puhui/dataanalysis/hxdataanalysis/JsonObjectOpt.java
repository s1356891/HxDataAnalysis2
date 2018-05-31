package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenb on 2018/5/24.
 */

public abstract class JsonObjectOpt {
    protected JSONObject jsonObject = new JSONObject();

    public JsonObjectOpt() {
    }

    public Object getJsonObj() {
        return this.jsonObject;
    }

    protected void setJson(String str, Object obj) {
        try {
            if(obj == null) {
                return;
            }

            if(this.getBoolean(obj)) {
                return;
            }

            this.jsonObject.put(str, obj);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

    }

    protected boolean getBoolean(Object obj) {
        if(obj instanceof JSONObject) {
            JSONObject var2 = (JSONObject)obj;
            if(var2.length() <= 0) {
                return true;
            }
        } else if(obj instanceof JSONArray) {
            JSONArray var3 = (JSONArray)obj;
            if(var3.length() <= 0) {
                return true;
            }
        }

        return false;
    }
}
