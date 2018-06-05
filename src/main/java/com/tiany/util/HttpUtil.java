package com.tiany.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public abstract class HttpUtil {

    /**
     * HTTP请求方法GET
     */
    public static final String http_method_get = "GET";

    /**
     * HTTP请求方法POST
     */
    public static final String http_method_post = "POST";

    /**
     * 默认编码格式
     */
    public static final String utf8_code = "UTF-8";

    /**
     * 进行HttpClient get连接
     *
     * @param isHttps 是否ssl链接
     * @param url
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static String get(boolean isHttps, String url)
            throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            if (!isHttps) {
                httpClient = HttpClients.createDefault();
            } else {
                httpClient = createSSLInsecureClient();
            }
            HttpGet httpget = new HttpGet(url);
            // httpget.addHeader(new BasicHeader("", ""));
            // httpget.addHeader("", "");
            CloseableHttpResponse response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 获取状态行
                // System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String out = EntityUtils.toString(entity, utf8_code);
                    return out;
                }
            }
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 进行HttpClient post连接
     *
     * @param isHttps     是否ssl链接
     * @param url
     * @param data
     * @param contentType
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String post(boolean isHttps, String url, String data, String contentType)
            throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            if (!isHttps) {
                httpClient = HttpClients.createDefault();
            } else {
                httpClient = createSSLInsecureClient();
            }
            HttpPost httpPost = new HttpPost(url);
            // (name, value);.addRequestHeader("Content-Type","text/html;charset=UTF-8");
            // httpPost.getParams().setParameter(HttpMethod.HTTP_CONTENT_CHARSET, "UTF-8");

            if (null != data) {
                StringEntity stringEntity = new StringEntity(data, utf8_code);
                stringEntity.setContentEncoding(utf8_code);
                if (null != contentType) {
                    stringEntity.setContentType(contentType);
                } else {
                    stringEntity.setContentType("application/json");
                }

                httpPost.setEntity(stringEntity);
            }

            RequestConfig requestConfig =
                    RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).build();// 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String out = EntityUtils.toString(entity, utf8_code);
                    return out;
                }
            }
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * HTTPS访问对象，信任所有证书
     *
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient createSSLInsecureClient()
            throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    /**
     * 原生方式请求
     *
     * @param isHttps       是否https
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return
     */
    public static String httpRequest(boolean isHttps, String requestUrl, String requestMethod, String outputStr) {
        HttpURLConnection conn = null;

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        PrintWriter printWriter = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (isHttps) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = {new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        // 检查客户端证书
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        // 检查服务器端证书
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        // 返回受信任的X509证书数组
                        return null;
                    }
                }};
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();// 从上述SSLContext对象中得到SSLSocketFactory对象
                httpsConn.setSSLSocketFactory(ssf);
                conn = httpsConn;
            }

            // 超时设置，防止 网络异常的情况下，可能会导致程序僵死而不继续往下执行
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);

            // Post 请求不能使用缓存
            conn.setUseCaches(false);

            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            // 设置请求方式（GET/POST），默认是GET
            conn.setRequestMethod(requestMethod);

            // 连接，上面对urlConn的所有配置必须要在connect之前完成，
            conn.connect();

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                outputStream = conn.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream, utf8_code);
                printWriter = new PrintWriter(outputStreamWriter);
                printWriter.write(outputStr);
                printWriter.flush();
                printWriter.close();
            }

            // 从输入流读取返回内容
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, utf8_code);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str).append("\n");
            }

            return buffer.toString();
        } catch (ConnectException ce) {
            ce.printStackTrace();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally { // 释放资源
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputStream = null;
            }

            if (null != outputStreamWriter) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputStreamWriter = null;
            }

            if (null != printWriter) {
                printWriter.close();
                printWriter = null;
            }

            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedReader = null;
            }

            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputStreamReader = null;
            }

            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputStream = null;
            }

            if (null != conn) {
                conn.disconnect();
                conn = null;
            }
        }
    }

    /**
     * map参数转list
     *
     * @param parameterMap
     * @return
     */
    public static List<NameValuePair> getParam(Map<String, String> parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> parmEntry = (Entry<String, String>) it.next();
            param.add(new BasicNameValuePair(parmEntry.getKey(), parmEntry.getValue()));
        }
        return param;
    }

    public static String http(String url, Map<String, String> params)
            throws Exception {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        System.out.println("send_url:" + url);
        System.out.println("send_data:" + sb.toString());
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            // // POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        // 一定要有返回值，否则无法把请求发送给server端。
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
