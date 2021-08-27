package love.loveing.shanguan.pojo;

import lombok.Data;
import org.springframework.objenesis.instantiator.perc.PercInstantiator;


@Data
public class httpBase {

    private String code;

    private String msg;

    private String data;
}
