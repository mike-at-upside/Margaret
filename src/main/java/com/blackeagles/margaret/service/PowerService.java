package com.blackeagles.margaret.service;

import org.springframework.stereotype.Component;

@Component
public class PowerService {

  public String bestPeriod(String text) {
    String normalisedText = normaliseText(text);

    return "couldn't get best for period: " + normalisedText;
  }

  public String worstPeriod(String text) {
    String normalisedText = normaliseText(text);

    return "couldn't get worst for period: " + normalisedText;
  }

  private String normaliseText(String text) {
    String trimmedText = text == null ? "" : text.trim();

    if (trimmedText.isEmpty()) {
      return "now";
    }

    return trimmedText.replaceAll(" +", " ");
  }
}
