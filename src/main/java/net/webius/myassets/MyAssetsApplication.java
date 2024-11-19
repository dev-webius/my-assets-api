package net.webius.myassets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyAssetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAssetsApplication.class, args);
    }

}
