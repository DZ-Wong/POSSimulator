/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pay;

import java.util.Map;

/**
 *
 * @author vip
 */
public class WxPay extends AbstractPay<Map<String,String>, String>{

    @Override
    public BarcodePayBody packBody(Map map) {

        BarcodePayBody requestBody = new BarcodePayBody();
        requestBody.setBarcode((String) map.get("barcode"));
        requestBody.setTxnAmt(Integer.valueOf((String)map.get("txnAmt")));
        requestBody.setOrderId((String) map.get("orderId"));
        requestBody.setCurrency(Integer.valueOf((String)map.get("currency")));
        return requestBody;
   
    }
    
}
