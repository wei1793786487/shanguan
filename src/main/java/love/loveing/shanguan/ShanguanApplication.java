package love.loveing.shanguan;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dtflys.forest.springboot.annotation.ForestScan;
import love.loveing.shanguan.pojo.requestAccessToken;
import love.loveing.shanguan.tool.securityTool;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.PrimitiveIterator;

@SpringBootApplication
@Configuration
@ForestScan(basePackages = "love.loveing.shanguan.requests")
public class ShanguanApplication {

    public static void main(String[] args) throws Exception {
            SpringApplication.run(ShanguanApplication.class, args);
    }
}
