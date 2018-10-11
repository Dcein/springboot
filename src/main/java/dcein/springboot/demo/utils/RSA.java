package dcein.springboot.demo.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 提供给第三方的RSA 帮助类
 * 完全基于JDK API ,自动适配密钥长度进行分段处理
 * @author junliang.li
 * @date 2018/04/10
 */
public class RSA {

    public static final String PRI_KEY_NAME = "RSAPrivateKey";

    public static final String PUB_KEY_NAME = "RSAPublicKey";

    /**
     * 加密算法，标准JDK实现是"RSA/None/PKCS1Padding";
     */
    private static final String ALGORITHM = "RSA";

    private static final Integer KEY_SIZE = 1024;


    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    public static final String LF = "\n";

    public static final String CR = "\r";

    public static final String SPACE = " ";

    public static final String EMPTY = "";

    private static Cipher CIPHER;

    static {
        try {
            CIPHER = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //初始化失败 CIPHER = Cipher.getInstance(ALGORITHM);
        }
    }


    /**
     * 加密
     *
     * @param key
     * @param raw
     * @return
     * @throws Exception
     */
    public static String encrypt(Key key, String raw) throws Exception {
        // Cipher负责完成加密或解密工作，基于RSA
        Cipher cipher = CIPHER != null ? CIPHER : Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] data = raw.getBytes(Charset.forName("UTF-8"));

        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        Integer keySize = getKeySize(key);
        // 对数据分段加密
        cipherSection(cipher, data, inputLen, out, offSet, i, (keySize / 8) - 11);
        byte[] encryptedData = out.toByteArray();
        out.close();

        return org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedData);
    }

    /**
     *  计算密钥长度
     * @param key 密钥
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private static Integer getKeySize(Key key) throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        BigInteger prime = null;
        if (key instanceof RSAPublicKey) {
            RSAPublicKeySpec keySpec = kf.getKeySpec(key, RSAPublicKeySpec.class);
            prime = keySpec.getModulus();
        } else if (key instanceof RSAPrivateKey) {
            RSAPrivateKeySpec keySpec = kf.getKeySpec(key, RSAPrivateKeySpec.class);
            prime = keySpec.getModulus();
        }
        assert prime != null;
        return prime.toString(2).length();

    }

    /**
     * @param cipher
     * @param data
     * @param inputLen
     * @param out
     * @param offSet
     * @param i
     * @param maxEncryptBlock
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static void cipherSection(Cipher cipher, byte[] data, int inputLen, ByteArrayOutputStream out, int offSet, int i, int maxEncryptBlock) throws IllegalBlockSizeException, BadPaddingException {
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
    }

    /**
     * 解密
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(Key key, String data) throws Exception {
        data = data.replace(SPACE, "+");
        // Cipher负责完成加密或解密工作，基于RSA
        Cipher cipher = CIPHER != null ? CIPHER : Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedData = org.apache.commons.codec.binary.Base64.decodeBase64(data);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        Integer keySize = getKeySize(key);
        // 对数据分段解密
        cipherSection(cipher, encryptedData, inputLen, out, offSet, i, keySize / 8);
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData,"UTF-8");
    }

    /**
     * 获取公钥对象
     *
     * @param publicKeyBase64
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static RSAPublicKey getRSAPublicKey(String publicKeyBase64)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec publicpkcs8KeySpec =
                new X509EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyBase64));

        return (RSAPublicKey) keyFactory.generatePublic(publicpkcs8KeySpec);
    }

    /**
     * 获取私钥对象
     *
     * @param privateKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey getRSAPrivateKey(String privateKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PKCS8EncodedKeySpec privatekcs8KeySpec =
                new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(privateKeyBase64));
        return (RSAPrivateKey) keyFactory.generatePrivate(privatekcs8KeySpec);
    }


    /**
     * 生成rsa公钥和密钥 base64 encode 文件
     *
     * @param publicKeyFilename
     * @param privateKeyFilename
     * @param password           not null
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @deprecated 对rsa进行base64编码后写入文件，文件夹不存在抛异常。
     */
    public static Map<String, String> generateBase64Key(String publicKeyFilename, String privateKeyFilename, String password)
            throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom(password.getBytes(Charset.forName("UTF-8")));
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        String stringPubKey = org.apache.commons.codec.binary.Base64.encodeBase64String(publicKeyBytes);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(publicKeyFilename));
        osw.write(stringPubKey);
        osw.close();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        String stringPriKey = org.apache.commons.codec.binary.Base64.encodeBase64String(privateKeyBytes);
        osw = new OutputStreamWriter(new FileOutputStream(privateKeyFilename));
        osw.write(stringPriKey);
        osw.close();

        Map<String, String> keymap = new HashMap<>(2);
        keymap.put(PRI_KEY_NAME, stringPriKey);
        keymap.put(PUB_KEY_NAME, stringPubKey);
        return keymap;
    }

    public static RSAPrivateKey getPemRSAPrivateKey(String contentBase64) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String privKeyPEM = contentBase64.replace("-----BEGIN PRIVATE KEY-----", EMPTY);
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", EMPTY);
        privKeyPEM = privKeyPEM.replace(LF, EMPTY).replace(CR, EMPTY).replace(SPACE, EMPTY).trim();

        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(privKeyPEM);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);

        return (RSAPrivateKey) kf.generatePrivate(spec);

    }

    public static RSAPublicKey getPemRSAPublicKey(String contentBase64) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String publicKeyPEM = contentBase64.replace("-----BEGIN PUBLIC KEY-----", EMPTY);
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", EMPTY);
        publicKeyPEM = publicKeyPEM.replace(LF, EMPTY).replace(CR, EMPTY).replace(SPACE, EMPTY).trim();

        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyPEM);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return (RSAPublicKey) kf.generatePublic(spec);

    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       元数据
     * @param privateKey 私钥
     * @return sign Base64 encode
     * @throws Exception
     */
    public static String sign(String data, RSAPrivateKey privateKey) throws Exception {
        byte[] decode = data.getBytes(Charset.forName("UTF-8"));
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(decode);

        return org.apache.commons.codec.binary.Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(String data, RSAPublicKey publicKey, String sign)
            throws Exception {
        sign = sign.replace(SPACE, "+");
        byte[] dData = data.getBytes(Charset.forName("UTF-8"));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(dData);

        byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(sign);
        // 验证签名是否正常
        return signature.verify(decode);
    }

}
