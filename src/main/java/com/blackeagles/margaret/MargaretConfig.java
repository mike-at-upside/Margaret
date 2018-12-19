package com.blackeagles.margaret;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.config.HerokuProperties;

@Component
public class MargaretConfig {

  @Bean
  public HerokuProperties herokuProperties() {
    return new HerokuProperties();
  }

  @Bean Quinton quinton(HerokuProperties herokuProperties) {
    return new Quinton(herokuProperties);
  }
}
