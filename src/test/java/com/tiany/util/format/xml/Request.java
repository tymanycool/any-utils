package com.tiany.util.format.xml;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request{
    Header header = new Header();
    Body body = new Body();

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Request{");
        sb.append("header=").append(header);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
