package com.blackeagles.margaret.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackeagles.margaret.model.SlackResponse;

@RestController
public class TestController {

  @GetMapping("/worst")
  public SlackResponse worst() {
    return new SlackResponse()
        .setText("Daniel is worst");
  }

}
