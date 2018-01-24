/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pay;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vip
 */
public abstract class AbstractPay <R extends Map, S extends String> {
    
    private static final Logger logger = Logger.getLogger("lavasoft");
    
    private String url = null;
    private String requestData = null;
    private GenericRequest genericRequest = new GenericRequest();
    public S handle(
        R map) throws HandleException, Exception {
   
        logger.setLevel(Level.INFO); 
        if (map.containsKey("orgFlag")){
            url = (String) map.get("orgUrl");
        }else{
            url = (String) map.get("url");
        }
        logger.info(url);
        //packHead
        GenericRequestHeader header = this.packHeader(map);
        //packBody
        BarcodePayBody body = this.packBody(map);
        
        genericRequest.setHeader(header);
        genericRequest.setBody(body);
              
        requestData = this.sign(genericRequest);
       
        //request
        S response = this.requestWithoutCert(url, requestData, 2000, 10000);
        logger.info(response);
    
        return response;
    }
    public GenericRequestHeader packHeader(Map<String, String> map)
    {
        String version = map.get("version");
        String txnId = map.get("txnId");
        String mid = map.get("mid");
        String tid = map.get("tid");
        String systrace = map.get("systrace");
        String batchNo = map.get("batchNo");
        String shiftNo = map.get("shiftNo");
        String orgId = map.get("orgId");
        long timestamp = System.currentTimeMillis();

        GenericRequestHeader requestHeader ;
        if (map.containsKey("orgFlag")){
            requestHeader = new GenericRequestHeader(version, txnId, orgId, mid, tid, systrace, batchNo, shiftNo, timestamp);
        }else{
            requestHeader = new GenericRequestHeader(version, txnId, mid, tid, systrace, batchNo, shiftNo, timestamp);
        }
        
        return requestHeader;
    }
    
    public  BarcodePayBody packBody(Map<String, String> map){
        BarcodePayBody requestBody = new BarcodePayBody();
        requestBody.setOrderId((String) map.get("orderId"));
        requestBody.setCurrency(Integer.valueOf((String)map.get("currency")));
        return requestBody;
    }
    

    
    protected String sign(GenericRequest request) {
        GenericRequest response = null;
        String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJxhz1eAhBR2P764OeHLXI+muyN9weGN2GBfWbMSkR0bI6pYZNUwCMxEn8tzcHkm2lkjOBTVDCtrwSEWJ8CUz9qe7lDo6DpDKHN6H+vZ7hTLtHt129sujDANQ+3vd9YX/E9Yjt8ShdcUKFQZO2kxGl74nUhGAO3xENV0U5/EKyvFAgMBAAECgYBGEKCoUMFctZpb5giw/L9s8UP5Y9fhKf9fNnlm/ROdWdKB+Pa8ebgHZVu++OrUrS65L0lqNAYu0imGnHmVFFeHpY5NSI80AwE7Gjq+WYOlN3IhMdWUk+9r/QzQ7vddmWVxQsX7pluFFjSmGel4B4VntPKQufL9Ada3aXa/xBC6hQJBANMgNO7K8fBFDyvqk9SjDHqVCQ2H2jgdDG4uTJRvcKwfb87UOOm1cnu1oQ1x35cpfKU/ozgglM7JHg6m6dLwXLMCQQC9nuIAIDlwh2TMZpAsiyr0Wv9BJjyrvAKrlqVpXQM3/w+t70mTP1lOt+dgx0wgqdgYiAK0D7LQPexi6NTH9gGnAkEAoQTcOg8gLG6PHqBetPrRpqAJ8n7dKJTHCTVYhJDlrvCe9nCXI2+Wa9FfjoB91az3epSpaEI5G+j5epVEmfNlzwJAdwDBwWZ35gFy5zzu+qWUnaqGS7LdnMHvwxRWV1vCa2AtzPFB8aFuQRL1qS0qv80YC71ARRUdGcfjFOgesifYPQJBAIjMECgpygLcUGdz3bjVQ5ru0K3Ed/70xio3vObLOko3Z4YfCFu7LtjVFv2NSfM9gfPzT7BY97LkKXUQviFZJ1U=";
//      String privateKey =        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANpXN9JdGaanss8csYOqcuQidzFCOtt2jXSIbr6EKPMdvAdqW94rvYkOCbfzbxMhf5MOh0pXZzlOtDW6j9Izu8m6XON7webi+1hKn+NTLCp4rEJXGMYokfCLcjxdT0RgaLHmvtCxJnPrHRwoQcZCoTbeicZu7wi2uclExGdw8+x9AgMBAAECgYAWY1sV9vXqYMe09Pw+w/uD6QTPyDrAxic7C8JPRr3sL+BX4lAJN/L3WrkkY5HAJG8wZQz6UnwDJE7WM4sLwsI+fJc5+FCrG9++CYMcnBAY0U41SGB2unOSOqty1W/yezNzy+AdeEsJG186d2M3b0lak8uTSRHzFy3BfjD1yBToAQJBAPYwcel/usvhqB4RXAmMaUoAAmCdWvmENj1DXxy7sX6Dt60Ek8hCZWkJlKWrL6o8wDrkCgy021qJtCjQIkBIagkCQQDjCqt+WTQlUMb7wEa1op3GyUGKYKI8Eh7xn7ilpsbgcwjhMpFQL5WUC2ITvkCOnCnypq9oH4EgbBhPq5XpjtvVAkEArdfLuHyvpSS1mHXdrghumQikpSC4ixVnT59xXFLVpWbRnuVqjbEE90UCqHHXeLjpbSx2RD653pb6lw9SAF6iMQJAd10JskCFqMSiCIMa6a1X08XFPlH1mS1RtWhqdDSNRD1WQscKHKUXt6CQbJ7OY+t4Jkk52L0PbxNFaMVl6EeiDQJACsDfZ53zV8xuragiL06aPVnKLRJrl1uS6xNZqDn8P3WzTkmkj/OTA224XjlgYYoy3YF8b+XNOWPjeZZ/lxSaPw==";      
        JSONObject data = new JSONObject(true);
        data.put("body", request.getBody());
        data.put("header", request.getHeader());

        try {
            response.setSign(SignatureHelper.rsaSignWithSHA256(privateKey, JSONObject.toJSONString(data)));
            response.setBody(request.getBody());
            response.setHeader(request.getHeader());
        } catch (Exception ex) {
            throw new RuntimeException("Signature ERROR:" + ex);
        }
        return response.toJsonString();
    }
    
    public S requestWithoutCert(String strUrl, String reqBody, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }

        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException var18) {
                var18.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }

        return (S)resp;
    }
}
