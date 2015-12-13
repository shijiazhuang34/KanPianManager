package com.meteor.model.po;

import com.meteor.kit.StringKit;

public class errpage {

    private String id;
    private String type;
    private String num;
    private String errmsg;
    private String searchkey;

    public errpage(String type,String num,String errmsg,String searchkey){
        this.id= StringKit.getMongoId();
        this.type=type;
        this.num=num;
        this.errmsg=errmsg;
        this.searchkey=searchkey;
    }

    public errpage(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
}
