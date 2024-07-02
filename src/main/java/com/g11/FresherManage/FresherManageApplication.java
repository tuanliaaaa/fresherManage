package com.g11.FresherManage;

import com.g11.FresherManage.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class FresherManageApplication {

  public static void main(String[] args) {
    SpringApplication.run(FresherManageApplication.class, args);
  }

}
