package xyz.appint.union.utils;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtils {

    public static String post(String url, Map<String, String> params) {
        return post(url, params, Charset.forName("UTF-8"));
    }

    public static String post(String url) {
        return post(url, null, Charset.forName("UTF-8"));
    }

    public static String post(String url, Map<String, String> params, Charset charset) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        DataInputStream inStream = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                if (nvps != null && nvps.isEmpty() == false) {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
                }
            }

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                inStream = new DataInputStream(entity.getContent());
                int length = inStream.available();
                byte[] b = new byte[length];
                inStream.read(b);
                result = new String(b, charset);
                EntityUtils.consume(entity);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, Charset.forName("UTF-8"));
    }

    public static String get(String url) {
        return post(url, null, Charset.forName("UTF-8"));
    }

    public static String get(String url, Map<String, String> params, Charset charset) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        DataInputStream inStream = null;
        String result = null;
        HttpGet httpGet = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                if (nvps != null && nvps.isEmpty() == false) {
                    builder.addParameters(nvps);
                }
            }

            httpGet = new HttpGet(builder.build());
            System.err.println(httpGet.getURI());
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                inStream = new DataInputStream(entity.getContent());
                int length = inStream.available();
                byte[] b = new byte[length];
                inStream.read(b);
                result = new String(b, charset);
                EntityUtils.consume(entity);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String upload(String url, File file) {
        return upload(url, null, file);
    }

    /**
     * 上传媒体文件
     *
     * @param url
     * @param params
     * @param file
     * @return
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.NoSuchProviderException
     * @throws java.security.KeyManagementException
     */
    public static String upload(String url, Map<String, String> params, File file) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        DataInputStream inStream = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                if (nvps != null && nvps.isEmpty() == false) {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
                }
            }

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            FileBody fileBody = new FileBody(file);
            builder.addPart(file.getName(), fileBody);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                inStream = new DataInputStream(entity.getContent());
                int length = inStream.available();
                byte[] b = new byte[length];
                inStream.read(b);
                result = new String(b, Charset.forName("UTF-8"));
                EntityUtils.consume(entity);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }

    /**
     * 下载资源
     *
     * @param url
     * @return
     * @throws java.io.IOException
     */
    public static Attachment download(String url) throws IOException {
        return download(url, null);
    }

    public static Attachment download(String url, Map<String, String> params) throws IOException {
        Attachment att = new Attachment();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        DataInputStream inStream = null;
        String result = null;
        HttpGet httpGet = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                if (nvps != null && nvps.isEmpty() == false) {
                    builder.addParameters(nvps);
                }
            }

            httpGet = new HttpGet(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                inStream = new DataInputStream(entity.getContent());


                BufferedInputStream bis = new BufferedInputStream(inStream);
                String fullName = getFileName(response);
                String relName = fullName.substring(0, fullName.lastIndexOf("."));
                String suffix = fullName.substring(relName.length() + 1);
                att.setFullName(fullName);
                att.setFileName(relName);
                att.setSuffix(suffix);
                //att.setContentLength(response.getHeaders("Content-Length"));
                //att.setContentType(conn.getHeaderField("Content-Type"));
                att.setFileStream(bis);

                EntityUtils.consume(entity);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                response.close();
            }
            return att;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        Map<String, String> params = new HashMap<String, String>();
        params.put("amt", "200");
        params.put("appid", "100720963");
        params.put("billno", "-APPDJSX15062-20130725-1513493433");
        params.put("payamt_coins", "0");
        params.put("payitem", "402881f73b5ec0a0013bacb2d2823295*20*1");
        params.put("ts", "1374736430");
        params.put("providetype", "0");
        params.put("pubacct_payamt_coins", "");
        params.put("token_id", "4EC9261D4BA699A4C8EF48217A3CA67928267");
        params.put("version", "v3");
        params.put("zoneid", "0");
        params.put("provide_errno", "测试中文");

        //System.out.println(HttpUtils.get("http://119.147.19.43//v3/pay/confirm_delivery" ,
        //		params ,
        //Charset.forName("UTF-8")));


        // params = new HashMap<String , String>();
        // params.put("username","user");
        // params.put("password","user");
        // params.put("lt","LT-4-oFRYwby1ZIKULSKTjheuTCPPLGHtwo");

        // System.out.println(HttpUtils.post("http://cb.qq.com/shop/mall/mall_list.html?iSubTypeId=98"
        // ,
        // params , Charset.forName("UTF-8")));

        CloseableHttpClient httpclient = HttpClients.createDefault();

        DataInputStream inStream = null;
        String result = null;
        try {
            List<NameValuePair> nvps = new ArrayList<>();
            if (params != null && !params.isEmpty()) {
                Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            String userAgent = "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I9300 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MicroMessenger/5.2.380";
            String url = "http://www.apiwx.com/index.php?ac=zp&c=o7MB9jqA-tKmv-9ClqlmSq1bye78&tid=101&id=650";
            url = "http://localhost:8080/app/demo/2";

            HttpGet httpPost = new HttpGet(url);
            httpPost.setHeader("User-Agent", userAgent);
            //httpPost.setHeader("Host", "cb.qq.com");
            httpPost.setHeader("Cache-Control", "max-age=0");
            httpPost.setHeader("Content-Type", "application/x-protobuf");
            httpPost.setHeader("Accept", "application/x-protobuf");

            httpPost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
            httpPost.setHeader("Accept-Language", "en-US,en;q=0.8");
            httpPost.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
            httpPost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
            //httpPost.setHeader("Referer", "http://cb.qq.com/shop/mall/");

	        
			/*
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader(
					"Cookie",
					"pgv_pvi=8676334592; ts_refer=item.cb.qq.com/mall/10050.html; pgv_pvid=5085424156; ts_uid=8993890344; pt2gguin=o0005734523; RK=DqHK9LFUWN; ptcz=23c5c476ddbbf092bd820fd5b2443a6a64c3d9f424c8b74d8d138da82befe374; _cb_uid=5734523%7C88733523%7C5bf887db6ed7cf964c8cb591e132d440; o_cookie=5734523; cbtuan_city=2800; pgv_si=s1434376192; pgv_info=ssid=s3854987656; ts_last=cb.qq.com/");
			httpPost.setHeader("Host", "cb.qq.com");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
			*/
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                String responseStr = EntityUtils.toString(entity, Charset.forName("UTF-8"));

                try {
                    // Convert from Unicode to UTF-8
                    byte[] utf8 = responseStr.getBytes("UTF-8");
                    // Convert from UTF-8 to Unicode
                    responseStr = new String(utf8, "UTF-8");

                    try {
                        // Convert from Unicode to UTF-8
                        String string = "\u8D60\u900110000\u79EF\u5206";
                        byte[] utf8Str = string.getBytes("UTF-8");
                        // Convert from UTF-8 to Unicode
                        string = new String(utf8Str, "UTF-8");
                        System.err.println("===" + string);
                    } catch (UnsupportedEncodingException e) {
                    }
                } catch (UnsupportedEncodingException e) {
                }

                System.err.println(responseStr);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
