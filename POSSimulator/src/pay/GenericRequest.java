package pay;



import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 浜ゆ槗閫氱敤璇锋眰銆�
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @Author leaves chen[leaves615@gmail.com]
 */
public class GenericRequest<T> {
    /**
     * 鍘熷璇锋眰json鏁版嵁銆�
     */
    private String originData;

    private String headerData;

    /**
     * 璇锋眰澶淬��
     */
    private RequestHeader header;

    /**
     * 涓氬姟json鏁版嵁銆�
     */
    private String bodyData;

    private JSONObject bodyObject;

    private T body;

    /**
     * 绛惧悕淇℃伅銆�
     */
    private String signature;

    private transient Map<String, Object> attributies = new HashMap<>();

    /**
     * 閫氳繃Json瀛楃涓插垵濮嬪寲浜ゆ槗璇锋眰銆�
     *
     * @param requestJsonString json璇锋眰瀛楃涓层��
     */
    public GenericRequest(String requestJsonString) {
        JSONObject requestObject = JSONObject.parseObject(requestJsonString);
        originData = requestJsonString;
        JSONObject headerObject = requestObject.getJSONObject("header");
        headerData = headerObject.toJSONString();
        header = requestObject.getObject("header", GenericRequestHeader.class);
        bodyObject = requestObject.getJSONObject("body");
        bodyData = bodyObject.toJSONString();
        signature = requestObject.getString("signture");
    }
    
    public GenericRequest(){
    
    }

    public void setOriginData(String originData) {
        this.originData = originData;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public void setBodyData(String bodyData) {
        this.bodyData = bodyData;
    }

    public void setBodyObject(JSONObject bodyObject) {
        this.bodyObject = bodyObject;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setAttributies(Map<String, Object> attributies) {
        this.attributies = attributies;
    }

    public String getOriginData() {
        return originData;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public String getBodyData() {
        return bodyData;
    }

    public JSONObject getBodyObject() {
        return bodyObject;
    }

    public T getBody() {
        return body;
    }

    protected void setBody(T body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public Map<String, Object> getAttributies() {
        return attributies;
    }

    public String supportTxnId() {
        return "all";
    }

    public String getHeaderData() {
        return headerData;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
    @Override
    public String toString() {
        return "GenericRequest{" + "originData='" + originData + '\'' + ", header=" + header +
               ", bodyData='" + bodyData + '\'' + ", signature='" + signature + '\'' + '}';
    }
}
