package love.loveing.shanguan.tool;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class securityTool {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
//        byte[] aesKey = getAESKey();
//        String s = bytesToBase64(aesKey);
////        System.out.println(s);
        String jaja = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAYYAF5a/+WuXTf3COIrH/Rtxy5qN3fr5hIlIAo3KpfudqooJuG4KpCjscpvmUvIelpbL88qPvqZn4yswnGPQqCyUKCzvaQ4LwxvyyMiGBhITYH2oELV4rZm9IJplbU7gqpkz/5o/Ysqe/qYaMUblxjt4f3X+FBFVBOCHRJ+m6YQIDAQAB\n";
        byte[] bytes = StringToByte(jaja);
        System.out.println(Arrays.toString(bytes));

    }


    public static byte[] StringToByte(String key) {
        byte[] bytes = Base64.decodeBase64(key.getBytes(StandardCharsets.UTF_8));
        System.out.println("String转换的key为");
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    public static byte[] getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(128);
        byte[] encoded = aes.generateKey().getEncoded();
        System.out.println("生成128位aes秘钥");
        System.out.println(Arrays.toString(encoded));
        return encoded;
    }

    public static String bytesToBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

    public static String getSkBase64(String base64AESKey) {
        String ak = "{\"sk\":\"" + base64AESKey + "\"}";
        return new String(Base64.encodeBase64(ak.getBytes(StandardCharsets.UTF_8)));
    }


    public static byte[] conversion(byte[] aesKey, byte[] rsaPublicKey) throws Exception {
        byte[] bArr3;
        if (aesKey == null) {
            throw new Exception("获取aea秘钥失败");
        } else if (rsaPublicKey != null) {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(rsaPublicKey));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePublic);
            int length = aesKey.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 > 0) {
                    if (i3 > 117) {
                        bArr3 = instance.doFinal(aesKey, i, 117);
                    } else {
                        bArr3 = instance.doFinal(aesKey, i, i3);
                    }
                    byteArrayOutputStream.write(bArr3, 0, bArr3.length);
                    i2++;
                    i = i2 * 117;
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            }
        } else {
            throw new Exception("获取公钥失败");
        }

    }
}
