package com.blackeagles.margaret.config;

public class HerokuProperties {

  public String getQuintonUrl() {
    return System.getenv("QUINTON_URL");
  }

}
