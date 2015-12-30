package com.win.funstory.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：WangShuang
 * Date: 2015/12/30 15:53
 * email：m15046658245_1@163.com
 */
public class PingLun2 {
    private  long userId;
    private  String userIcon;
    private  String username;
    private String content;


    public PingLun2(JSONObject object)throws JSONException {

        if(! object.isNull("user")) {
            userIcon = object.getJSONObject("user").getString("icon");
            username = object.getJSONObject("user").getString("login");
            userId = object.getJSONObject("user").getLong("id");
        }
        content=object.getString("content");
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
