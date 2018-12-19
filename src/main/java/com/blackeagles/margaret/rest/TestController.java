package com.blackeagles.margaret.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackeagles.margaret.model.SlackResponse;

@RestController
public class TestController {

  @PostMapping("/worst")
  public SlackResponse worst() {
    return new SlackResponse()
        .setText("Daniel is worst");
  }

}
