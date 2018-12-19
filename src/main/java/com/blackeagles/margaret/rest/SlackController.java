package com.blackeagles.margaret.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackeagles.margaret.model.SlackResponse;

@RestController
public class SlackController {

  @PostMapping("/slack/best")
  public SlackResponse bestPeriod() {
    return new SlackResponse()
        .setResponseType(SlackResponse.RESPONSE_TYPE_IN_CHANNEL)
        .setText("could not get best period");
  }

  @PostMapping("/slack/worst")
  public SlackResponse worstPeriod() {
    return new SlackResponse()
        .setResponseType(SlackResponse.RESPONSE_TYPE_IN_CHANNEL)
        .setText("could not get worst period");
  }

}