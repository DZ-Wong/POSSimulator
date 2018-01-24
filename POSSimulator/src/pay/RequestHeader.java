package pay;

/**
 * 璇锋眰澶淬��
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @Author leaves chen[leaves615@gmail.com]
 */
public interface RequestHeader {

    /**
     * 鑾峰彇浜ゆ槗绫诲瀷銆�
     * @return 浜ゆ槗绫诲瀷浠ｇ爜銆�
     */
    String getTxnId();

    /**
     * 鑾峰彇鍟嗘埛鍙枫��
     * @return 鍟嗘埛鍙枫��
     */
    String getMid();
   /**
     * 获取机构号
     * @return
     */
    String getOrgId();
    /**
     * 鑾峰彇缁堢鍙枫��
     * @return 缁堢鍙枫��
     */
    String getTid();

    /**
     * 鑾峰彇缁堢娴佹按鍙枫��
     * @return 缁堢娴佹按鍙枫��
     */
    String getSystrace();

    /**
     * 鑾峰彇鎵规鍙枫��
     * @return 鎵规鍙枫��
     */
    String getBatchNo();

    /**
     * 鑾峰彇鐝鍙枫��
     * @return 鐝鍙枫��
     */
    String getShiftNo();

    /**
     * 鑾峰彇鐗堟湰鍙枫��
     * @return 鐗堟湰鍙枫��
     */
    String getVersion();

    /**
     * 鑾峰彇缁堢璇锋眰鏃堕棿銆�
     * @return 缁堢璇锋眰鏃堕棿銆�
     */
    long getTimestamp();
}
