package io.github.rojae.authcoreapi.dto;

import io.github.rojae.authcoreapi.common.enums.PlatformType;

public class SignupResponse {
  private String email;
  private String name;
  private String platformType;

  public SignupResponse() {
  }

  public SignupResponse(String email, String name, String platformType) {
    this.email = email;
    this.name = name;
    this.platformType = platformType;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlatformType() {
    return platformType;
  }

  public void setPlatformType(String platformType) {
    this.platformType = platformType;
  }
}
