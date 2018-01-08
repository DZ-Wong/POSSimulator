package pay;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * rsa签名验签工具类
 * Create By wengjianbin on 2017/12/27
 *
 * @Author wengjianbin [921244209@qq.com]
 */
public class SignatureHelper {

    private static PrivateKey getPrivateKeyFromPKCS8(String privateKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(buffer));
    }

    private static PublicKey getPublicKeyFromX509(String publicKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] buffer = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(buffer));
    }

    /**
     * 生成rsa签名
     * @param keyString  私钥
     * @param sourceData 签名数据
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String rsaSignWithSHA256 (String keyString, String sourceData)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        String sign;
        PrivateKey privateKey = getPrivateKeyFromPKCS8(keyString, "RSA");
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(sourceData.getBytes());
        sign = Base64.getEncoder().encodeToString(signature.sign());

        return sign;
    }

    /**
     * 验签
     * @param keyString    公钥
     * @param sourceData   签名数据
     * @param sign         签名
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Boolean rsaVerifyWithSHA256(String keyString, String sourceData, String sign)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        Boolean result;
        PublicKey publicKey = getPublicKeyFromX509(keyString, "RSA");
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(sourceData.getBytes());
        result = signature.verify(Base64.getDecoder().decode(sign));

        return result;
    }
}
