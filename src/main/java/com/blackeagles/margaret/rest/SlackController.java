package com.blackeagles.margaret.rest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackeagles.margaret.model.SlackResponse;
import com.blackeagles.margaret.service.PowerService;

@RestController
public class SlackController {

  private final PowerService powerService;

  public SlackController(PowerService powerService) {
    this.powerService = powerService;
  }

  @PostMapping("/slack/best")
  public SlackResponse bestPeriod(@ModelAttribute("text") String text) {
    return new SlackResponse()
        .setResponseType(SlackResponse.RESPONSE_TYPE_IN_CHANNEL)
        .setText(powerService.bestPeriod(text));
  }

  @PostMapping("/slack/worst")
  public SlackResponse worstPeriod(@ModelAttribute("text") String text) {
    return new SlackResponse()
        .setResponseType(SlackResponse.RESPONSE_TYPE_IN_CHANNEL)
        .setText(powerService.worstPeriod(text));
  }

}
