package love.loveing.shanguan;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import love.loveing.shanguan.pojo.httpBase;
import love.loveing.shanguan.pojo.login;
import love.loveing.shanguan.pojo.requestAccessToken;
import love.loveing.shanguan.pojo.userInfo;
import love.loveing.shanguan.requests.SecurityClient;
import love.loveing.shanguan.tool.securityTool;
import org.apache.catalina.security.SecurityUtil;
import org.apache.logging.log4j.util.Base64Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.PrimitiveIterator;


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

        String s1 = HexUtil.encodeHexStr(aesKey);

        System.out.println("十六进制");
        System.out.println(s1);

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
        String stringUniqueCode = new String(decrypt);

        //其实这个生成秘钥在那用都行 一次抓取就可以一直用的 后面的请求用到的数据其实是aes的秘钥  跟这个生成的 uniqueCode
        System.out.println(stringUniqueCode);


//       下面的登录逻辑
//      构造accessToken
        String srequestAccessTokenBase64 = securityTool.encrypt(new requestAccessToken(), aesKey);
        System.out.println(srequestAccessTokenBase64);


        //构造请求body
        login login1 = new login();
        login1.setUserId("215071204118");
        login1.setPassw("243010");
        String encrypt = securityTool.encrypt(login1, aesKey);


        httpBase login = securityClient.login(encrypt, stringUniqueCode, srequestAccessTokenBase64);


        String userinfo = new String(aes.decrypt(login.getData()));
        userInfo userInfo = JSON.parseObject(userinfo, userInfo.class);
        System.out.println(userinfo);

    }


}
