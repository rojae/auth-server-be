package io.github.rojae.authserver.dto;

import io.github.rojae.authserver.common.enums.PlatformType;

public class ServiceAuthRequest {
  private String email;
  private PlatformType platformType;

  public ServiceAuthRequest() {
  }

  public ServiceAuthRequest(String email, PlatformType platformType) {
    this.email = email;
    this.platformType = platformType;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public PlatformType getPlatformType() {
    return platformType;
  }

  public void setPlatformType(PlatformType platformType) {
    this.platformType = platformType;
  }
}
