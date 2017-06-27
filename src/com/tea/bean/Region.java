package com.tea.bean;

/**
 * Created by Administrator on 2016/10/31.
 */
public class Region {
    private String regionCode;  /*2，4位数*/
    private String fullName;
    private String shortName;
    private String parentCode;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Region(String regionCode, String fullName, String shortName, String parentCode) {
        this.regionCode = regionCode;
        this.fullName = fullName;
        this.shortName = shortName;
        this.parentCode = parentCode;
    }

    public Region() {
    }
}
