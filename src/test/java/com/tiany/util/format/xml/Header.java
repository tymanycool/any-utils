package com.tiany.util.format.xml;

public class Header{
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Header{");
        sb.append("id=").append(id);
        sb.append(", reqId='").append(reqId).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append('}');
        return sb.toString();
    }
}