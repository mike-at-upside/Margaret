package com.blackeagles.margaret.model;

import java.util.Objects;

public class SlackResponse {

  private String responseType;
  private String text;

  public String getResponseType() {
    return responseType;
  }

  public SlackResponse setResponseType(final String responseType) {
    this.responseType = responseType;
    return this;
  }

  public String getText() {
    return text;
  }

  public SlackResponse setText(final String text) {
    this.text = text;
    return this;
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SlackResponse)) {
      return false;
    }
    final SlackResponse that = (SlackResponse) o;
    return Objects.equals(responseType, that.responseType) &&
        Objects.equals(text, that.text);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(responseType, text);
  }
}
