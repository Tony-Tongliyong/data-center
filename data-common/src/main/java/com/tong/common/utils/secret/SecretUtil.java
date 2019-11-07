package com.tong.common.utils.secret;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: CommandStreamGobbler
 * @time: 2019/1/16 10:05
 * @desc:
 */
public class SecretUtil {

    private static final String ENCODING = "UTF-8";

    private static final String KEY_ALGORITHM_RSA = "RSA";
    
    private static final String KEY_ALGORITHM_3DES = "DESede";
    
    private static final String CIPHER_ALGORITHM_3DES = "DESede/ECB/PKCS5Padding";
    
    private static final Base64 BASE64 = new Base64();

    /**
     * BASE64加密
     *
     * @param plaintextBytes
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] plaintextBytes) throws Exception {
        return new String(BASE64.encode(plaintextBytes), ENCODING);
    }

    /**
     * BASE64解密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String cipherText) {
        return BASE64.decode(cipherText);
    }
    
    /**
     * 加密
     * @param data 裸的原始数据
     * @param key  经过base64加密的公钥(RSA)或者裸密钥(3DES)
     * */
    public static String encrypt(String data, String key, String method) throws Exception {
        if (SecretUtil.KEY_ALGORITHM_RSA.equals(method)) {
            return SecretUtil.encryptRSA(data, key);
        } else if (SecretUtil.KEY_ALGORITHM_3DES.equals(method)) {
            return SecretUtil.encrypt3DES(data, key);
        } else {
            throw new Exception(String.format("系统编程错误,不支持的加密类型：%s", method));
        }
    }
    
    /**
     * 解密
     * @param data 已经经过base64加密的密文
     * @param key  已经经过base64加密私钥(RSA)或者裸密钥(3DES)
     * */
    public static String decrypt(String data, String key, String method) throws Exception {
        if (SecretUtil.KEY_ALGORITHM_RSA.equals(method)) {
            return SecretUtil.decryptRSA(data, key);
        } else if (SecretUtil.KEY_ALGORITHM_3DES.equals(method)) {
            return SecretUtil.decrypt3DES(data, key);
        } else {
            throw new Exception(String.format("系统编程错误,不支持的加密类型：%s", method));
        }
    }

    /**
     * RSA加密
     * 用公钥加密 encryptByPublicKey
     *
     * @param data 裸的原始数据
     * @param key  经过base64加密的公钥
     * @return 结果也采用base64加密
     * @throws Exception
     */
    public static String encryptRSA(String data, String key) throws Exception {
        try {
            // 对公钥解密，公钥被base64加密过
            byte[] keyBytes = decryptBASE64(key);

            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return encryptBASE64(cipher.doFinal(data.getBytes(ENCODING)));
        } catch (Exception e) {
            throw new Exception(String.format("rsa加密出错：%s", e));
        }
    }
    
    /**
     * RSA解密
     * 用私钥解密
     *
     * @param data 已经经过base64加密的密文
     * @param key  已经经过base64加密私钥
     * @return
     * @throws Exception
     */
    public static String decryptRSA(String data, String key) throws Exception {
        try {
            // 对密钥解密
            byte[] keyBytes = decryptBASE64(key);

            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return new String(cipher.doFinal(decryptBASE64(data)), ENCODING);
        } catch (Exception e) {
            throw new Exception(String.format("rsa解密出错：%s", e));
        }
    }
    
    /**
     * 初始化密钥 for RSA ALGORITHM
     *
     * @return
     * @throws Exception
     */
    public static String[] initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM_RSA);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String[] publicAndPrivateKey = {
                encryptBASE64(publicKey.getEncoded()),
                encryptBASE64(privateKey.getEncoded())};

        return publicAndPrivateKey;
    }
    
    /**
     * DES加密 DESede
     * 用密钥加密
     *
     * @param data 裸的原始数据
     * @param key  加密的密钥
     * @return 结果也采用base64加密
     * @throws Exception
     */
    public static String encrypt3DES(String data, String key) throws Exception {
        try {
            // 生成密钥
            SecretKey desKey = new SecretKeySpec(build3DesKey(key),
                    KEY_ALGORITHM_3DES);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_3DES);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return encryptBASE64(cipher.doFinal(data.getBytes(ENCODING)));
        } catch (Exception e) {
            throw new Exception(String.format("3重DES加密出错：%s", e));
        }
    }
    
    /**
     * DES解密
     * 用密钥解密
     *
     * @param data 已经经过base64加密的密文
     * @param key  解密的密钥
     * @return
     * @throws Exception
     */
    public static String decrypt3DES(String data, String key) throws Exception {
        try {
            // 生成密钥
            SecretKey desKey = new SecretKeySpec(build3DesKey(key),
                    KEY_ALGORITHM_3DES);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_3DES);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return new String(cipher.doFinal(decryptBASE64(data)), ENCODING);
        } catch (Exception e) {
            throw new Exception(String.format("3重DES解密出错：%s", e));
        }
    }
    
    /**
     * 根据字符串生成密钥字节数组
     * 
     * @param keyStr 密钥字符串
     * @return key 符合DESede标准的24byte数组
     */
    private static byte[] build3DesKey(String keyStr) throws Exception {
        try {
            // 声明一个24位的字节数组，默认里面都是0，warn: 字符串0(48)和数组默认值0不一样，统一字符串0(48)
            byte[] key = "000000000000000000000000".getBytes(ENCODING);
            byte[] temp = keyStr.getBytes(ENCODING);
            if (key.length > temp.length) {
                // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
                System.arraycopy(temp, 0, key, 0, temp.length);
            } else {
                // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
                System.arraycopy(temp, 0, key, 0, key.length);
            }
            return key;
        } catch (Exception e) {
            throw new Exception(String.format("构建三重DES密匙出错：%s", e));
        }
    }

    public static void main(String[] args) throws Exception {

        String data = "这是条原始数据";
        String key = "这是密码";

        // Base64加密
        String base64Key = encryptBASE64(key.getBytes());
        System.out.println("Base64加密：" + base64Key);
        // Base64解密
        byte[] bytes = decryptBASE64(base64Key);
        System.out.println("Base64解密：" + new String(bytes));


        // RSA公钥和私钥
        String[] publicAndPrivateKey = initKey();
        // 公钥
        String publicKey = publicAndPrivateKey[0];
        // 私钥
        String privateKey = publicAndPrivateKey[1];
        // RSA加密
        String encryptRSAData = encryptRSA(data,publicKey);
        System.out.println("RSA加密：" + encryptRSAData);
        // RSA解密
        String decryptRSAData = decryptRSA(encryptRSAData, privateKey);
        System.out.println("RSA解密：" + decryptRSAData);


        // 3DES加密
        String encrypt3DESData = encrypt3DES(data, key);
        System.out.println("3DES加密：" + encrypt3DESData);
        // 3DES解密
        String decrypt3DESData = decrypt3DES(encrypt3DESData, key);
        System.out.println("3DES解密：" + decrypt3DESData);

        String data1 = decrypt("","20190516172400","DESede");
        System.out.println(data1);
    }
}
