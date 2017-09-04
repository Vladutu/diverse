package com.ace.ucv;

public class ZipCode {

    private String prefix;

    private String code;

    public ZipCode(String prefix, String code) {
        this.prefix = prefix;
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
