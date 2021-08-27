package love.loveing.shanguan.requests;

import com.dtflys.forest.annotation.*;
import com.sun.org.apache.bcel.internal.classfile.Code;
import love.loveing.shanguan.pojo.httpBase;

@BaseRequest(
        baseURL = "http://222.206.86.42:8081/mobileapi_ydxy/open/",
        headers = {
                "User-Agent:Dalvik/2.1.0 (Linux; U; Android 6.0.1; MuMu Build/V417IR)",
                "Host:222.206.86.42:8081",
                "Connection:close",
                "Accept-Encoding: gzip, deflate",
                "Asym-Key: private",
        }
)
public interface SecurityClient {
    @Request(
            url = "security/asym/key",
            headers = {
                    "Asym-Key:public "
            })
    httpBase getPublicKey();

    @PostRequest(
            url = "security/asym/key",
            data = "${0}",
            headers = {
                    "Content-Type:application/json"
            })
    httpBase getUniqueCode(String info);

    @PostRequest(
            url = "auth/login",
            contentType = "text/plain"
            )
    httpBase login(@Body String body,@Header("Unique-Code") String UniqueCode, @Header("Access-Token") String accessToken );





}


