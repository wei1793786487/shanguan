package love.loveing.shanguan.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class requestAccessToken implements Serializable {

    private String clientVersion="3.2.0";

    private String sessionKey;

    private String userId;

    private String userType;

    private String xxdm;

}

