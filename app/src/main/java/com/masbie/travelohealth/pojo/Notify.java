
package com.masbie.travelohealth.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notify {

    @SerializedName("t")
    @Expose
    private String t;
    @SerializedName("l")
    @Expose
    private Integer l;
    @SerializedName("m")
    @Expose
    private String m;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

}
