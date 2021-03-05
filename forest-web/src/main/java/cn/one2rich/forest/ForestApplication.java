package cn.one2rich.forest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.one2rich.forest"})
public class ForestApplication {

  public static void main(String[] args) {
    SpringApplication.run(ForestApplication.class, args);
  }
}
