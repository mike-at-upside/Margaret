package com.blackeagles.margaret.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/worst")
  public String worst() {
    return "Daniel is worst";
  }

}
