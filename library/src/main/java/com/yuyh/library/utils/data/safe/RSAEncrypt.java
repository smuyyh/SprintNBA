package com.yuyh.library.utils.data.safe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author yuyh.
 * @date 16/4/10.
 */
public class RSAEncrypt {

    public static final String KEY_ALGORTHM="RSA";
    public static final String SIGNATURE_ALGORITHM="MD5withRSA";

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String privateKey_Str = "";

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    public RSAEncrypt(){
    }

    public RSAEncrypt(String publicKey){
        try {
            loadPublicKey(publicKey);
        } catch (Exception e) {
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     *
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            in.close();
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     *
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer               = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory       = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec  = new X509EncodedKeySpec(buffer);
            publicKey                   = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     *
     * @return 是否成功
     *
     * @throws Exception
     */
    public void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br   = new BufferedReader(new InputStreamReader(in));
            String readLine;
            StringBuilder sb    = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            in.close();
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer               = Base64.decode(privateKeyStr, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory       = KeyFactory.getInstance("RSA");
            privateKey                  = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    /**
     * 数据加密方法
     *
     * @param sourceData 明文数据
     *
     * @return 加密后的数据
     */
    public String encrypt(byte[] sourceData) {
        String result = "";
        try {
            byte[] cipher   = encrypt(publicKey, sourceData);
            result          = byte2HexStr(cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密方法
     *
     * @param privateKey 私钥
     * @param cipherData  加密数据
     *
     * @return 解密后的明文数据
     */
    public String decrypt(String privateKey, byte[] cipherData) {
        String result = "";
        try {
            if (privateKey_Str.equals("")) {
                privateKey_Str = privateKey;
                loadPrivateKey(privateKey);
            }
            byte[] cipher       = hex2byte(cipherData);
            byte[] content      = decrypt(this.privateKey, cipher);
            result              = new String(content);
//            result          = byte2HexStr(decryKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密方法
     *
     * @param cipherData  加密数据
     *
     * @return 解密后的明文数据
     */
    public String decrypt(byte[] cipherData){
        String result = "";
        try {
            byte[] cipher   = hex2byte(cipherData);
            byte[] content      = decrypt(privateKey, cipher);
            result              = new String(content);
//            result          = byte2HexStr(decryKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密过程
     *
     * @param publicKey     公钥
     * @param sourceData 明文数据
     *
     * @return
     *
     * @throws Exception 加密过程中的异常信息
     */
    private byte[] encrypt(RSAPublicKey publicKey, byte[] sourceData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        //进行加密
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(sourceData);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     *
     * @return 明文
     *
     * @throws Exception 解密过程中的异常信息
     */
    private byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        //进行解密
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(cipherData);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密
     *
     * @param cipherData  加密数据
     *
     * @return 解密后的明文数据
     */
    public String decryptPublicKey(String cipherData) {
        String result = "";
        try {
            byte[] data = Base64.decode(cipherData, Base64.DEFAULT);
            byte[] content = decryptPublicKey(this.publicKey, data);
            result = new String(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   public String decryptPublicKey(byte[] data) {
        String result = "";
        try {
            byte[] content = decryptPublicKey(this.publicKey, data);
            result = new String(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey     公钥
     * @param sourceData 明文数据
     *
     * @return
     *
     * @throws Exception 加密过程中的异常信息
     */
    private byte[] decryptPublicKey(RSAPublicKey publicKey, byte[] sourceData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(sourceData);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data	//加密数据
     * @param privateKey	//私钥
     * @return
     * @throws Exception
     */
    public String sign(byte[] data,String privateKey)throws Exception{
        //解密私钥
        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);
        byte[] signByte = signature.sign();
        return new String(Base64.encode(signByte, Base64.DEFAULT),"UTF-8");
    }

    /**
     * 校验数字签名
     * @param data	加密数据
     * @param sign	数字签名
     */
    public boolean verify(byte[] data,String sign)throws Exception{
        //加密后的文件
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Base64.decode(sign, Base64.DEFAULT));
    }

    /**
     * 十进制字节数据转十六进制字符串
     *
     * @param data 输入数据十进制
     *
     * @return 十六进制内容
     */
    private String byte2HexStr(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {

            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            builder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);

            //取出字节的低四位 作为索引得到相应的十六进制标识符
            builder.append(HEX_CHAR[(data[i] & 0x0f)]);

        }
        return builder.toString();
    }

    /**
     * 十六进制字节数组转十进制
     * @param hexData  输入十六进制数据
     * @return    输出十进制数据
     */
    private byte[] hex2byte(byte[] hexData) {
        if ((hexData.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");

        byte[] out = new byte[hexData.length / 2];
        for (int n = 0; n < hexData.length; n += 2) {
            String item = new String(hexData, n, 2);
            out[n / 2]  = (byte) Integer.parseInt(item, 16);
        }

        return out;
    }

    /**
     * 随机生成密钥对
     */
    private void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        publicKey = (RSAPublicKey) keyPair.getPublic();
    }

}
