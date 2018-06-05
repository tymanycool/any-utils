package com.tiany.util.format.xml;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Request extends Parent{

    Header header = new Header();
    Body body = new Body();

    private int id;

    List<Header> listHeader = new ArrayList<>();

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


    public List<Header> getListHeader() {
        return listHeader;
    }

    public void setListHeader(List<Header> listHeader) {
        this.listHeader = listHeader;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Request{");
        sb.append("header=").append(header);
        sb.append(", body=").append(body);
        sb.append(", id=").append(id);
        sb.append(", listHeader=").append(listHeader);
        sb.append('}');
        return sb.toString();
    }
}
