package io.github.rojae.authserver.dto;

import io.github.rojae.authserver.common.enums.PlatformType;

public class ServiceSignupResponse {
  private String email;
  private String name;
  private PlatformType platformType;

  public ServiceSignupResponse() {
  }

  public ServiceSignupResponse(String email, String name, PlatformType platformType) {
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

  public PlatformType getPlatformType() {
    return platformType;
  }

  public void setPlatformType(PlatformType platformType) {
    this.platformType = platformType;
  }
}
