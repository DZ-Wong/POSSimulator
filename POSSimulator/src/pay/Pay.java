package pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.ReadAndWrite;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.R;

public class Pay {

    private static final Logger logger = Logger.getLogger("lavasoft");

    public String barcodePay(Map<String, String> map) throws Exception {
        String requestData = null;
        logger.setLevel(Level.INFO); 
        
        String strUrl ;
        if (map.containsKey("orgFlag")){
            strUrl = map.get("orgUrl");
        }else{
            strUrl = map.get("url");
        }
        requestData = packPayRequest(map);

        logger.info(requestData);
        logger.info(strUrl);
        String respStr = requestWithoutCert(strUrl, requestData, 2000, 10000);
        
//        JSONObject data = JSON.parseObject(respStr);
//        if (data.getObject())
//        Map map2 = (Map)data;
        checkSignature("null");
        logger.info(respStr);
        return respStr;


    }

    public String barcodeQuery(Map<String, String> map)  throws Exception{
        logger.setLevel(Level.INFO); 
        String requestData = null;
        
        String strUrl ;
       
        if (map.containsKey("orgFlag")){
            strUrl = map.get("orgUrl");
        }else{
            strUrl = map.get("url");
        }
        requestData = packQueryRequest(map);

        logger.info(requestData);
        String respStr = requestWithoutCert(strUrl, requestData, 2000, 10000);

        logger.info(respStr);
        
        return respStr;
    }
    
    public String barcodeRefund(Map<String, String> map)  throws Exception{

        String requestData = null;
        
        String strUrl ;
       
        if (map.containsKey("orgFlag")){
            strUrl = map.get("orgUrl");
        }else{
            strUrl = map.get("url");
        }
        requestData = packRefundRequest(map);

        logger.info(requestData);
        logger.info(strUrl);
        String respStr = requestWithoutCert(strUrl, requestData, 2000, 10000);

        logger.info(respStr);
        
        return respStr;
    }

    public String packPayRequest(Map<String, String> map) {
        GenericRequest genericRequest = new GenericRequest();
        GenericRequest genericRequestWithSign = new GenericRequest();
        String version = map.get("version");
        String txnId = map.get("pay");
        String mid = map.get("mid");
        String tid = map.get("tid");
        String systrace = map.get("systrace");
        String batchNo = map.get("batchNo");
        String shiftNo = map.get("shiftNo");
        String orgId = map.get("orgId");

        long timestamp = System.currentTimeMillis();
        GoodInfo goodsInfo = new GoodInfo();
        goodsInfo.setGoodId("Mars");
        goodsInfo.setNum(1);
        goodsInfo.setPrice(1);

        GenericRequestHeader requestHeader ;
        if (map.containsKey("orgFlag")){
            requestHeader = new GenericRequestHeader(version, txnId, orgId, mid, tid, systrace, batchNo, shiftNo, timestamp);
        }else{
            requestHeader = new GenericRequestHeader(version, txnId, mid, tid, systrace, batchNo, shiftNo, timestamp);
        }
        BarcodePayBody requestBody = new BarcodePayBody();
        requestBody.setBarcode(map.get("barcode"));
        requestBody.setTxnAmt(Integer.valueOf(map.get("txnAmt")));
        requestBody.setOrderId(map.get("orderId"));
//        requestBody.setGoodInfo(goodsInfo);
        requestBody.setCurrency(Integer.valueOf(map.get("currency")));

        genericRequest.setHeader(requestHeader);
        genericRequest.setBody(requestBody);

        sign(genericRequest, genericRequestWithSign);
    
        return genericRequestWithSign.toJsonString();
    }
    
     public String packQueryRequest(Map<String, String> map) {
        GenericRequest genericRequest = new GenericRequest();
        GenericRequest genericRequestWithSign = new GenericRequest();
        String version = map.get("version");
        String txnId = map.get("query");
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
        BarcodePayBody requestBody = new BarcodePayBody();
        requestBody.setOrderId(map.get("orderId"));
        requestBody.setCurrency(Integer.valueOf(map.get("currency")));
        genericRequest.setHeader(requestHeader);
        genericRequest.setBody(requestBody);
              
        sign(genericRequest, genericRequestWithSign);
       
        return genericRequestWithSign.toJsonString();
    }
    
      public String packRefundRequest(Map<String, String> map) {
        GenericRequest genericRequest = new GenericRequest();
        GenericRequest genericRequestWithSign = new GenericRequest();
        String version = map.get("version");
        String txnId = map.get("refund");
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
        BarcodePayBody requestBody = new BarcodePayBody();
        requestBody.setOrderId(map.get("orderId"));

        genericRequest.setHeader(requestHeader);
        genericRequest.setBody(requestBody);
        sign(genericRequest, genericRequestWithSign);
       
        return genericRequestWithSign.toJsonString();
    }
     
 
    protected void sign(GenericRequest request, GenericRequest response) {
        String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJxhz1eAhBR2P764OeHLXI+muyN9weGN2GBfWbMSkR0bI6pYZNUwCMxEn8tzcHkm2lkjOBTVDCtrwSEWJ8CUz9qe7lDo6DpDKHN6H+vZ7hTLtHt129sujDANQ+3vd9YX/E9Yjt8ShdcUKFQZO2kxGl74nUhGAO3xENV0U5/EKyvFAgMBAAECgYBGEKCoUMFctZpb5giw/L9s8UP5Y9fhKf9fNnlm/ROdWdKB+Pa8ebgHZVu++OrUrS65L0lqNAYu0imGnHmVFFeHpY5NSI80AwE7Gjq+WYOlN3IhMdWUk+9r/QzQ7vddmWVxQsX7pluFFjSmGel4B4VntPKQufL9Ada3aXa/xBC6hQJBANMgNO7K8fBFDyvqk9SjDHqVCQ2H2jgdDG4uTJRvcKwfb87UOOm1cnu1oQ1x35cpfKU/ozgglM7JHg6m6dLwXLMCQQC9nuIAIDlwh2TMZpAsiyr0Wv9BJjyrvAKrlqVpXQM3/w+t70mTP1lOt+dgx0wgqdgYiAK0D7LQPexi6NTH9gGnAkEAoQTcOg8gLG6PHqBetPrRpqAJ8n7dKJTHCTVYhJDlrvCe9nCXI2+Wa9FfjoB91az3epSpaEI5G+j5epVEmfNlzwJAdwDBwWZ35gFy5zzu+qWUnaqGS7LdnMHvwxRWV1vCa2AtzPFB8aFuQRL1qS0qv80YC71ARRUdGcfjFOgesifYPQJBAIjMECgpygLcUGdz3bjVQ5ru0K3Ed/70xio3vObLOko3Z4YfCFu7LtjVFv2NSfM9gfPzT7BY97LkKXUQviFZJ1U=";
//      String privateKey =        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANpXN9JdGaanss8csYOqcuQidzFCOtt2jXSIbr6EKPMdvAdqW94rvYkOCbfzbxMhf5MOh0pXZzlOtDW6j9Izu8m6XON7webi+1hKn+NTLCp4rEJXGMYokfCLcjxdT0RgaLHmvtCxJnPrHRwoQcZCoTbeicZu7wi2uclExGdw8+x9AgMBAAECgYAWY1sV9vXqYMe09Pw+w/uD6QTPyDrAxic7C8JPRr3sL+BX4lAJN/L3WrkkY5HAJG8wZQz6UnwDJE7WM4sLwsI+fJc5+FCrG9++CYMcnBAY0U41SGB2unOSOqty1W/yezNzy+AdeEsJG186d2M3b0lak8uTSRHzFy3BfjD1yBToAQJBAPYwcel/usvhqB4RXAmMaUoAAmCdWvmENj1DXxy7sX6Dt60Ek8hCZWkJlKWrL6o8wDrkCgy021qJtCjQIkBIagkCQQDjCqt+WTQlUMb7wEa1op3GyUGKYKI8Eh7xn7ilpsbgcwjhMpFQL5WUC2ITvkCOnCnypq9oH4EgbBhPq5XpjtvVAkEArdfLuHyvpSS1mHXdrghumQikpSC4ixVnT59xXFLVpWbRnuVqjbEE90UCqHHXeLjpbSx2RD653pb6lw9SAF6iMQJAd10JskCFqMSiCIMa6a1X08XFPlH1mS1RtWhqdDSNRD1WQscKHKUXt6CQbJ7OY+t4Jkk52L0PbxNFaMVl6EeiDQJACsDfZ53zV8xuragiL06aPVnKLRJrl1uS6xNZqDn8P3WzTkmkj/OTA224XjlgYYoy3YF8b+XNOWPjeZZ/lxSaPw==";      
        JSONObject data = new JSONObject(true);
        data.put("body", request.getBody());
        data.put("header", request.getHeader());
//        data.put("body", request.getBody());
        try {
            response.setSign(SignatureHelper.rsaSignWithSHA256(privateKey, JSONObject.toJSONString(data)));
            response.setBody(request.getBody());
            response.setHeader(request.getHeader());
        } catch (Exception ex) {
            throw new RuntimeException("Signature ERROR:" + ex);
        }

    }
        /**
     * 签名验证。
     *
     * @param request 请求参数。
     */
    protected void checkSignature(String request) throws Exception {
        if(true)
            return;
        String data ="{\"header\":{\"batchNo\":\"180118\",\"systrace\":\"000331\",\"mid\":\"01\",\"version\":\"V1.0\",\"tid\":\"44010609\",\"shiftNo\":\"01\",\"timestamp\":1516241777671,\"txnId\":\"841100\"},\"body\":{\"orderId\":\"20180118180118000332\",\"currency\":156,\"barcode\":\"134727842524314096\",\"txnAmt\":1}}";
        String sign = "k9BfKERJq6eMkTX8LX85CGLJvsG2XsQYDz9A4KmZCEsNUzDvJqpALdKaRDFbMITwr+rILP7suMxi4yfgu0CxcEOxbtWmnjw9XrjRMBq2wVeSKdxAXda2EBwbNK67ZFUp1PCvjB9BXHqjG+4ezpm7TkNA8eGTs5UlcK7ZGjmmgy0=";
//        data.remove("signature");
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcYc9XgIQUdj++uDnhy1yPprsjfcHhjdhgX1mzEpEdGyOqWGTVMAjMRJ/Lc3B5JtpZIzgU1Qwra8EhFifAlM/anu5Q6Og6Qyhzeh/r2e4Uy7R7ddvbLowwDUPt73fWF/xPWI7fEoXXFChUGTtpMRpe+J1IRgDt8RDVdFOfxCsrxQIDAQAB";
        try {
            if (false ==
                    SignatureHelper.rsaVerifyWithSHA256(publicKey, data, sign)) {
                throw new Exception("InValid Signature");
            }
        } catch (Exception ex) {
            throw new Exception("CheckSignature ERROR:" + ex);
        }

    }

    public String requestWithoutCert(String strUrl, String reqBody, int connectTimeoutMs, int readTimeoutMs) throws Exception {
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

        return resp;
    }

//    public String requestWithCert(String strUrl, Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
//        String UTF8 = "UTF-8";
//        String reqBody = null;
//        URL httpUrl = new URL(strUrl);
//        char[] password = this.config.getMchID().toCharArray();
//        InputStream certStream = this.config.getCertStream();
//        KeyStore ks = KeyStore.getInstance("PKCS12");
//        ks.load(certStream, password);
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, password);
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(kmf.getKeyManagers(), (TrustManager[])null, new SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setConnectTimeout(connectTimeoutMs);
//        httpURLConnection.setReadTimeout(readTimeoutMs);
//        httpURLConnection.connect();
//        OutputStream outputStream = httpURLConnection.getOutputStream();
//        outputStream.write(reqBody.getBytes(UTF8));
//        InputStream inputStream = httpURLConnection.getInputStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
//        StringBuffer stringBuffer = new StringBuffer();
//        String line = null;
//
//        while((line = bufferedReader.readLine()) != null) {
//            stringBuffer.append(line);
//        }
//
//        String resp = stringBuffer.toString();
//        if(stringBuffer != null) {
//            try {
//                bufferedReader.close();
//            } catch (IOException var24) {
//                ;
//            }
//        }
//
//        if(inputStream != null) {
//            try {
//                inputStream.close();
//            } catch (IOException var23) {
//                ;
//            }
//        }
//
//        if(outputStream != null) {
//            try {
//                outputStream.close();
//            } catch (IOException var22) {
//                ;
//            }
//        }
//
//        if(certStream != null) {
//            try {
//                certStream.close();
//            } catch (IOException var21) {
//                ;
//            }
//        }
//
//        return resp;
//    }
}
