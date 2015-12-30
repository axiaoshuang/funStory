package com.win.funstory.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * author：WangShuang
 * Date: 2015/12/29 16:08
 * email：m15046658245_1@163.com
 */
public class chunTu  implements Serializable {
    private  long userId;
    private  String userIcon;
    private  String username;
    private String content;
    private String image;

    private long pid;

    private int up;
    private int comments_count;
    private  int  share_count;


    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public chunTu(JSONObject object)throws JSONException{

       if(! object.isNull("user")) {
           userIcon = object.getJSONObject("user").getString("icon");
           username = object.getJSONObject("user").getString("login");
           userId = object.getJSONObject("user").getLong("id");
       }
        content=object.getString("content");
        pid=object.getLong("id");
        up=object.getJSONObject("votes").getInt("up");


        comments_count=object.getInt("comments_count");
        share_count=object.getInt("share_count");

       if( !object.isNull("image")) {
           image = object.getString("image");
       }
       }
    public chunTu(String userIcon, String username, String content, String image) {
        this.userIcon = userIcon;
        this.username = username;
        this.content = content;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
