package love.loveing.shanguan;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ForestScan(basePackages = "love.loveing.shanguan.requests")
public class ShanguanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanguanApplication.class, args);
    }

}
