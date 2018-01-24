package pay;


public class BarcodePayBody {
    private long     txnAmt;
    private int      currency;
    private String   barcode;
    private String   orderId;
    private GoodInfo goodInfo;

    public long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public GoodInfo getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public String toString() {
        return "BarcodePayBody{" + "txnAmt=" + txnAmt + ", currency=" + currency + ", barcode=" + barcode + ", orderId=" + orderId + ", goodInfo=" + goodInfo + '}';
    }
    
    
}
