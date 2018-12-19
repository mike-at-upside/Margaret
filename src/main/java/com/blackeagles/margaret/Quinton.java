package com.blackeagles.margaret;

import java.io.IOException;

import com.blackeagles.margaret.config.HerokuProperties;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Quinton {

  private final String url;

  public Quinton(HerokuProperties herokuProperties) {
    this.url = herokuProperties.getQuintonUrl();
  }

  public void say(String message) {
    OkHttpClient client = new OkHttpClient();

    // Create request for remote resource.
    Request request = new Request.Builder()
        .url(url)
        .post(RequestBody.create(MediaType.parse("application/json"), "{\"text\": \"" + message + "\"}"))
        .build();

    // Execute the request and retrieve the response.
    try (Response response = client.newCall(request).execute()) {
      // Do nothing.
    } catch (IOException e) {
      System.out.println("Call to " + url + "failed");
      e.printStackTrace();
    }
  }
}
