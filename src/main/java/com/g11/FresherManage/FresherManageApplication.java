package com.g11.FresherManage;

import com.g11.FresherManage.configs.FilePropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FilePropertiesConfig.class)
public class FresherManageApplication {

  public static void main(String[] args) {
    SpringApplication.run(FresherManageApplication.class, args);
  }

}
