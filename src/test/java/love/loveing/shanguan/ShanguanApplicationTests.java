package love.loveing.shanguan;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import love.loveing.shanguan.pojo.httpBase;
import love.loveing.shanguan.requests.SecurityClient;
import love.loveing.shanguan.tool.securityTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


@SuppressWarnings("ALL")
@SpringBootTest
class ShanguanApplicationTests {

    @Autowired
    private SecurityClient securityClient;

    @Test
    void contextLoads() throws Exception {

//        security security = new security();

//        第一步 创建aes秘钥
        byte[] aesKey = securityTool.getAESKey();

//        第二步 将aes转为base64
        String base64Key = securityTool.bytesToBase64(aesKey);

        System.out.println(base64Key);

//        第三步  构造{"sk":"上一步已经被转为base64的秘钥"}
        String skBase64 = securityTool.getSkBase64(base64Key);
        System.out.println(skBase64);

//       将sk转数组
        byte[] skBytes = securityTool.StringToByte(skBase64);
        System.out.println("将sk转数组");

        System.out.println(Arrays.toString(skBytes));

//        获取公钥
        httpBase publicKey = securityClient.getPublicKey();

        String publicKeyString = publicKey.getData();
        System.out.println(publicKeyString);
        //        公钥转数组
        byte[] publicKeyByte = securityTool.StringToByte(publicKeyString);
        System.out.println(Arrays.toString(publicKeyByte));
//
//        根据他的规则转换
        byte[] conversion = securityTool.conversion(skBytes, publicKeyByte);
        System.out.println(Arrays.toString(conversion));

        //构造 请求需要的参数
        String security = securityTool.bytesToBase64(conversion);
        System.out.println(security);


        //发送请求
        httpBase uniqueCode = securityClient.getUniqueCode(security);
        System.out.println(uniqueCode);

        //解密请求
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, aesKey);
        byte[] decrypt = aes.decrypt(uniqueCode.getData());

        //获取 字符串 uniqueCode
        System.out.println(new String(decrypt));

    }


}
