package dcein.springboot.demo.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: DingCong
 * @Description: RSA加密工具
 * @@Date: Created in 14:05 2018/8/9
 */
public class RSAUtil {

    /**
     * RSA加密算法
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 默认的加解密算法为RSA
     * “RSA”算法每次采用同一个公钥计算同一段文字，所得的密文都是一样的；
     */
    public static final String ENCRYPT_ALGORITHM = "RSA";
    /**
     * 提供者
     */
    private static final BouncyCastleProvider PROVIDER = new BouncyCastleProvider();
    /**
     * 密钥长度，默认为1024
     * 该值关系到块加密的大小，该值不宜过大，否则会降低加密效率
     */
    private static final int KEY_SIZE = 1024;
    /**
     * cipher map
     */
    private static Map<String, Cipher> CIPHER_MAP = new HashMap<String, Cipher>();

    /**
     * 生成秘钥到文件
     * @param publicKeyFilePath 公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @return
     * @throws Exception
     */
    public static File[] generateKeysAsFiles(String publicKeyFilePath, String privateKeyFilePath) throws Exception{
        File[] files = new File[2];
        KeyPair keyPair = generateKeyPair();
        files[0] = saveRSAPublicKeyAsFile((RSAPublicKey)keyPair.getPublic(), publicKeyFilePath);
        files[1] = saveRSAPrivateKeyAsFile((RSAPrivateKey)keyPair.getPrivate(), privateKeyFilePath);
        return files;
    }

    /**
     * 加密
     * 采用默认的加解密算法“RSA”，对待加密明文数据进行加密
     * @param text 待加密的明文数据
     * @param key 加密的密钥
     * @return 加密后数据的字节数组
     * @throws Exception
     */
    public static String encrypt(String text, Key key) throws Exception{
        return encrypt(text, key, ENCRYPT_ALGORITHM);
    }

    /**
     * 解密
     * 采用默认的解密算法“RSA”进行解密
     * @param text 已经加密的数据
     * @param key 解密的密钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decrypt(String text, Key key) throws Exception{
        return decrypt(text, key, ENCRYPT_ALGORITHM);
    }

    /**
     * 根据字符串生成为私钥
     * @param key 待转换的字符串
     * @return 私钥
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromString(String key) throws Exception{
        KeyFactory keyFactory=KeyFactory.getInstance(ALGORITHM, PROVIDER);
        org.apache.commons.codec.binary.Base64 b64=new org.apache.commons.codec.binary.Base64();
        EncodedKeySpec privateKeySpec=new PKCS8EncodedKeySpec(b64.decode(key));
        PrivateKey privateKey=keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    /**
     * 根据字符串生成为公钥
     * @param key 待转换的字符串
     * @return 公钥
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromString(String key) throws Exception{
        org.apache.commons.codec.binary.Base64 b64=new org.apache.commons.codec.binary.Base64();
        KeyFactory keyFactory=KeyFactory.getInstance(ALGORITHM, PROVIDER);
        EncodedKeySpec publicKeySpec=new X509EncodedKeySpec(b64.decode(key));
        PublicKey publicKey=keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }
    
    /**
     * 生成密钥对
     * @return 密钥对（公钥/私钥）
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 将RSA公钥保存为文件并返回该文件
     * @param publicKey
     * @param filePath
     * @return
     * @throws Exception
     */
    public static File saveRSAPublicKeyAsFile(RSAPublicKey publicKey, String filePath) throws Exception{
        String publicKeyStr = getKeyAsString(publicKey);
        saveFile(publicKeyStr, filePath);
        return new File(filePath);
    }

    /**
     * 将RSA私钥保存为文件并返回该文件
     * @param privateKey 私钥
     * @param filePath 文件路径
     * @throws Exception
     */
    public static File saveRSAPrivateKeyAsFile(RSAPrivateKey privateKey, String filePath) throws Exception{
        String privateKeyStr = getKeyAsString(privateKey);
        saveFile(privateKeyStr, filePath);
        return new File(filePath);
    }

    /**
     * 将秘钥转换为字符串
     * @param key 密钥
     * @return 转换之后的字符串
     */
    public static String getKeyAsString(Key key){
        byte[] keyBytes = key.getEncoded();
        org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
        return b64.encodeToString(keyBytes);
    }

    /**
     * 将文本内容保存到文件中
     * @param content 文本内容
     * @param filePath 文件路径
     * @throws Exception
     */
    private static void saveFile(String content, String filePath) throws Exception{
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath));
        try{
            osw.write(content, 0, content.length());
            osw.flush();
        }finally{
            osw.close();
        }
    }


    /**
     * 加密
     * 采用特定的加解密算法，对待加密明文数据进行加密
     * @param data 待加密的明文数据
     * @param key 加密的密钥
     * @param encryptAlgorithm 加密算法
     * @return 加密后数据的字节数组
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key, String encryptAlgorithm) throws Exception {
        try {
            Cipher cipher = CIPHER_MAP.get(encryptAlgorithm);
            if(null == cipher){
                cipher = Cipher.getInstance(encryptAlgorithm, PROVIDER);
            }
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127 byte,加密后为128个byte;
            //因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);//获得加密块加密后的大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize){
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                }
                else{
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                //这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                //而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * BASE64编码
     * @param bytes 待编码字节数组
     * @return 编码之后的字符串
     */
    public static String encodeBASE64(byte[] bytes) throws Exception{
        org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
        return b64.encodeToString(bytes);
    }

    /**
     * 加密
     * 采用特定的加密算法，对待加密的明文数据进行加密
     * @param text 待加密的明文数据
     * @param key 加密的密钥
     * @param encryptAlgorithm 加密算法
     * @return 加密后的密文数据
     * @throws Exception
     */
    public synchronized static String encrypt(String text, Key key, String encryptAlgorithm) throws Exception{
        String encryptedText;
        byte[] cipherText = encrypt(text.getBytes("UTF8"), key, encryptAlgorithm);
        encryptedText = encodeBASE64(cipherText);
        return encryptedText;
    }


    /**
     * 解密
     * 采用默认的加解密算法“RSA”进行解密
     * @param text
     * @param key
     * @param decryptAlgorithm
     * @return
     * @throws Exception
     */
    public synchronized static String decrypt(String text, Key key, String decryptAlgorithm) throws Exception{
        String result;
        byte[] dectyptedText=decrypt(decodeBASE64(text), key, decryptAlgorithm);
        result=new String(dectyptedText,"UTF8");
        return result;
    }

    /**
     * BASE64解码
     * @param text 待解码字符串
     * @return 解码之后的字节数组
     * @throws IOException
     */
    public static byte[] decodeBASE64(String text) throws Exception{
        org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
        return b64.decode(text);
    }

    /**
     * 解密
     * @param raw 已经加密的数据
     * @param key 解密的密钥
     * @param decryptAlgorithm 解密算法
     * @return 解密后的明文字节数组
     * @throws Exception
     */
    public static byte[] decrypt(byte[] raw, Key key, String decryptAlgorithm) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        try {
            Cipher cipher = CIPHER_MAP.get(decryptAlgorithm);
            if(null == cipher){
                cipher = Cipher.getInstance(decryptAlgorithm, PROVIDER);
            }
            cipher.init(Cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally{
            bout.close();
        }
    }


    public static void main(String[] args) throws Exception {
        PublicKey publicKeyFromString = getPublicKeyFromString("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCKQuBCakqx5TaospFhXFzCDPDbdwECKZQXxuiMn5toiWmEmYaYMN3hYp01+eMeVvBOV4MZ5tvYxSN+lQ13mpAphsMIkmic2HjnEHQSj7WQ3fr1ygQFqv+A+tgj3x3bOn0m+U804grdYBoUe8OkY+oFJqUDnu4alS/7jLVEgOeRwIDAQAB");
        PrivateKey privateKeyFromString = getPrivateKeyFromString("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMIpC4EJqSrHlNqiykWFcXMIM8Nt3AQIplBfG6Iyfm2iJaYSZhpgw3eFinTX54x5W8E5Xgxnm29jFI36VDXeakCmGwwiSaJzYeOcQdBKPtZDd+vXKBAWq/4D62CPfHds6fSb5TzTiCt1gGhR7w6Rj6gUmpQOe7hqVL/uMtUSA55HAgMBAAECgYAI/JAFFPFjgtanl/P3a8m9zUS4EumlYNEcfzUZjhoT8Paqd8eKCQMtu7HriTgKZG4+xJekc1cvvKJC6rHjHGbxKkl+GLlJ70BnvyHMSVbDgJYJX9ZXhO86bkY0Nof5Yh2SxqSxxhkB/pQlIgyoBas0EyagiVoX2oTm5WPdajKroQJBAPvsSVc1oAJGVlIHYozGdNdKxR4SyOkBEeZpKTVYNA7Wy0RK1KnuB1WLb/uEhZ6Cx0cYBnsE+M57zBHojZXY2MUCQQDFTXLTAcfhxnQCpJdV6Z/uINU1HMJYMjviCmO9CZnzJjdG/miaHrDRrqorutE4i4yomRW8bU6UW11xYY+NttObAkA8n5jqlqoBwiFlrgdGLz/Qy1pyKT0V7T78fpP/FVG5vHC/524T0ocLS/nPwg1RntELx7lPg1vIfLwYFylzRPKtAkEApJuiAcNJlObyIMZa8DVy1cQUvTQaPGTkqwiJ/8wD4eVvVs6oTHTwhm/Xz5YUOb3Lg1n81XDIj2POiOqhTtVKowJANk2wiG61tJrFTyxQcz9WEdP90Tjv0qzizpxZXGXQNfbjPN1v0nwHuuqlKdggLQTaQ/7HSsV5a4a9bCq55oIrOA==");
        String encrypt = encrypt("jdbc:mysql://106.12.14.74:3306/cloud_spring?useUnicode=true&characterEncoding=UTF-8&useSSL=true&zeroDateTimeBehavior=convertToNull", publicKeyFromString);
        System.out.println("密文:" + encrypt);
        String decrypt = decrypt(encrypt, privateKeyFromString);
        System.out.println(decrypt);
    }

}

