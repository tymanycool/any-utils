package com.tiany.util.format.xml;

public class Header{
    String reqId;
    String sign;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Header{");
        sb.append("reqId='").append(reqId).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append('}');
        return sb.toString();
    }
}