package io.github.rojae.authserver.dto;

import io.github.rojae.authserver.common.enums.PlatformType;

public class ServiceSignupRequest {
  private String email;
  private String password;
  private String name;
  private PlatformType platformType;
  private String profileImage;

  public ServiceSignupRequest() {
  }

  public ServiceSignupRequest(String email, String password, String name, PlatformType platformType,
      String profileImage) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.platformType = platformType;
    this.profileImage = profileImage;
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

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
