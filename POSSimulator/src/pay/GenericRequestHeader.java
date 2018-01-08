package pay;

/**
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @Author leaves chen[leaves615@gmail.com]
 */
public class GenericRequestHeader implements RequestHeader {
    private String version;
    private String txnId;
    private String mid;
    private String tid;
    private String systrace;
    private String batchNo;
    private String shiftNo;
    private long timestamp;

    public GenericRequestHeader(
        String version, String txnId, String mid, String tid, String systrace, String batchNo,
        String shiftNo, long timestamp) {
        this.version = version;
        this.txnId = txnId;
        this.mid = mid;
        this.tid = tid;
        this.systrace = systrace;
        this.batchNo = batchNo;
        this.shiftNo = shiftNo;
        this.timestamp = timestamp;
    }

    @Override
    public String getTxnId() {
        return txnId;
    }

    @Override
    public String getMid() {
        return mid;
    }

    @Override
    public String getTid() {
        return tid;
    }

    @Override
    public String getSystrace() {
        return systrace;
    }

    @Override
    public String getBatchNo() {
        return batchNo;
    }

    @Override
    public String getShiftNo() {
        return shiftNo;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "GenericRequestHeader{" + "version='" + version + '\'' + ", txnId='" + txnId + '\'' +
               ", mid='" + mid + '\'' + ", tid='" + tid + '\'' + ", systrace='" + systrace + '\'' +
               ", batchNo='" + batchNo + '\'' + ", shiftNo='" + shiftNo + '\'' + ", timestamp=" +
               timestamp + '}';
    }


}
