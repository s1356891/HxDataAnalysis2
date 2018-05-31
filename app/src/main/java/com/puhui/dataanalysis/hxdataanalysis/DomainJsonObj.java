package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by chenb on 2018/5/25.
 */

public class DomainJsonObj extends JsonObjectOpt {
    public DomainJsonObj(String str1, String str2) {
        this.setJson("domain", str1);
        this.setJson("name", str2);
    }

    public void setData(Map map) {
        if (map != null) {
            JSONObject jsonObject = new JSONObject(map);
            this.setJson("data",jsonObject);
        }
    }
}
