package com.tiany;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.junit.Ignore;

import javax.xml.namespace.QName;


@Ignore
public class TestAxisWebservice {

    public static void main(String[] args) throws Exception {
        //服务地址
        String requestUrl = "https://jrtest.xmitic.com:18082/fms/services/searchService?wsdl";
        String requestXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.inf.creditapp.app/\"><soapenv:Header/><soapenv:Body><ws:search><request><brNo>wanda</brNo><txCode>2202</txCode><reqDate>20190429</reqDate><reqTime>08:57:43</reqTime><token>20190429085743002384</token><reqSerial>CJ0000162019042908585383152</reqSerial><encryptSts>1</encryptSts><signSts>1</signSts><content>a7CMIMo94SPYkNZCbc07tgZHpHkZozRBmBfEPwlFoW5T/4xtXJqX8aN8RRnF18aAj5eM2k9h/crZKdQmmet2XsaPtH2VjgVL5XHWr4fc4b2HIAk8/m+1fa0jlphEwWZ7ZiIz8w0Yf+Sjvqk+sIzWl5P+enh6RjhkFrL6MWy6fsh2dWib3r5LsbkWYQmgPxZCOAKsnwoyWG63UDkFzbLPMhTiWAM5l/xUxC1bI0OwPF3/I092Bw9RlMO6hAG+CvVaT1cf9fbpL+jVXeV343tMgH4SkgRcbo2LcQEdhaMIGWns1IoYg6lLvFwPBbFnHlZl58TMNVHHCDarNgFjkXE25w==</content><sign>GZfJ5jPM03Q8eYFYGvxLszdYCe/Emi51zpAFV0E9HIWN3MJVaG3FqaSaLqBq7IrCw/idtUM/YkU1qzC/GJveB/rK7dIBUWweJi92NFjV+dje2k+O8O6xqo1WYFuuV4fx5B1ZWyWuQ0oVXBo6SKg8mZoH4vIFJ1dqT9EKv/nyQW2299dI38DQKGIgqbQkTK0Pfgo4smMw0IDtne07Qqcy+s5dHcc1vAhP7s8VAMonsI86AqStXkBFapcoYGpjUssUiXoZ+QJTsvj9mEXB8g+L+Z57XqolY+DQmI40SqEwqfonDcx9nCrW96CIeC9TJPbQeLYB8vgj20ZL2PdVTFy8kg==</sign></request></ws:search></soapenv:Body></soapenv:Envelope>";
        Object[] object = new Object[]{requestXml};//请求参数
        Service service = new Service();
        org.apache.axis.client.Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(requestUrl);// 远程调用路径
        // 调用的命名空间和方法名
        call.setOperationName(new QName("http://ws.inf.creditapp.app/", "search"));
        call.setUseSOAPAction(true);
        //可以在wsdl中找个这个地址
        call.setSOAPActionURI("http://partnertest.mypicc.com.cn:80/ecooperation/webservice/insure");
        //命名空间和参数名，参数名不可以随便写,参数名可以在wsdl文件中找到
        call.addParameter(new QName("http://ws.inf.creditapp.app/", "brNo"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
        call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
        call.setTimeout(100000);//超时
        String result = (String) call.invoke(object);// 远程调用
        System.out.println(result);

    }
}
