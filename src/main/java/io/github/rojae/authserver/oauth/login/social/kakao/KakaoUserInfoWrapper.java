package io.github.rojae.authserver.oauth.login.social.kakao;

import java.util.Date;

public class KakaoUserInfoWrapper {
    public long id;
    public Date connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    public KakaoUserInfoWrapper() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getConnected_at() {
        return connected_at;
    }

    public void setConnected_at(Date connected_at) {
        this.connected_at = connected_at;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public KakaoAccount getKakao_account() {
        return kakao_account;
    }

    public void setKakao_account(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    @Override
    public String toString() {
        return "KakaoUserInfoWrapper{" +
                "id=" + id +
                ", connected_at=" + connected_at +
                ", properties=" + properties +
                ", kakao_account=" + kakao_account +
                '}';
    }

    public static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
        public boolean has_age_range;
        public boolean age_range_needs_agreement;
        public String age_range;
        public boolean has_birthday;
        public boolean birthday_needs_agreement;
        public String birthday;
        public String birthday_type;
        public boolean has_gender;
        public boolean gender_needs_agreement;
        public String gender;

        public KakaoAccount() {
        }

        public boolean isProfile_nickname_needs_agreement() {
            return profile_nickname_needs_agreement;
        }

        public void setProfile_nickname_needs_agreement(boolean profile_nickname_needs_agreement) {
            this.profile_nickname_needs_agreement = profile_nickname_needs_agreement;
        }

        public boolean isProfile_image_needs_agreement() {
            return profile_image_needs_agreement;
        }

        public void setProfile_image_needs_agreement(boolean profile_image_needs_agreement) {
            this.profile_image_needs_agreement = profile_image_needs_agreement;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public boolean isHas_email() {
            return has_email;
        }

        public void setHas_email(boolean has_email) {
            this.has_email = has_email;
        }

        public boolean isEmail_needs_agreement() {
            return email_needs_agreement;
        }

        public void setEmail_needs_agreement(boolean email_needs_agreement) {
            this.email_needs_agreement = email_needs_agreement;
        }

        public boolean isIs_email_valid() {
            return is_email_valid;
        }

        public void setIs_email_valid(boolean is_email_valid) {
            this.is_email_valid = is_email_valid;
        }

        public boolean isIs_email_verified() {
            return is_email_verified;
        }

        public void setIs_email_verified(boolean is_email_verified) {
            this.is_email_verified = is_email_verified;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isHas_age_range() {
            return has_age_range;
        }

        public void setHas_age_range(boolean has_age_range) {
            this.has_age_range = has_age_range;
        }

        public boolean isAge_range_needs_agreement() {
            return age_range_needs_agreement;
        }

        public void setAge_range_needs_agreement(boolean age_range_needs_agreement) {
            this.age_range_needs_agreement = age_range_needs_agreement;
        }

        public String getAge_range() {
            return age_range;
        }

        public void setAge_range(String age_range) {
            this.age_range = age_range;
        }

        public boolean isHas_birthday() {
            return has_birthday;
        }

        public void setHas_birthday(boolean has_birthday) {
            this.has_birthday = has_birthday;
        }

        public boolean isBirthday_needs_agreement() {
            return birthday_needs_agreement;
        }

        public void setBirthday_needs_agreement(boolean birthday_needs_agreement) {
            this.birthday_needs_agreement = birthday_needs_agreement;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday_type() {
            return birthday_type;
        }

        public void setBirthday_type(String birthday_type) {
            this.birthday_type = birthday_type;
        }

        public boolean isHas_gender() {
            return has_gender;
        }

        public void setHas_gender(boolean has_gender) {
            this.has_gender = has_gender;
        }

        public boolean isGender_needs_agreement() {
            return gender_needs_agreement;
        }

        public void setGender_needs_agreement(boolean gender_needs_agreement) {
            this.gender_needs_agreement = gender_needs_agreement;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "KakaoAccount{" +
                "profile_nickname_needs_agreement=" + profile_nickname_needs_agreement +
                ", profile_image_needs_agreement=" + profile_image_needs_agreement +
                ", profile=" + profile +
                ", has_email=" + has_email +
                ", email_needs_agreement=" + email_needs_agreement +
                ", is_email_valid=" + is_email_valid +
                ", is_email_verified=" + is_email_verified +
                ", email='" + email + '\'' +
                ", has_age_range=" + has_age_range +
                ", age_range_needs_agreement=" + age_range_needs_agreement +
                ", age_range='" + age_range + '\'' +
                ", has_birthday=" + has_birthday +
                ", birthday_needs_agreement=" + birthday_needs_agreement +
                ", birthday='" + birthday + '\'' +
                ", birthday_type='" + birthday_type + '\'' +
                ", has_gender=" + has_gender +
                ", gender_needs_agreement=" + gender_needs_agreement +
                ", gender='" + gender + '\'' +
                '}';
        }
    }

    public static class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;

        public Profile() {
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThumbnail_image_url() {
            return thumbnail_image_url;
        }

        public void setThumbnail_image_url(String thumbnail_image_url) {
            this.thumbnail_image_url = thumbnail_image_url;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        public boolean isIs_default_image() {
            return is_default_image;
        }

        public void setIs_default_image(boolean is_default_image) {
            this.is_default_image = is_default_image;
        }

        @Override
        public String toString() {
            return "Profile{" +
                "nickname='" + nickname + '\'' +
                ", thumbnail_image_url='" + thumbnail_image_url + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", is_default_image=" + is_default_image +
                '}';
        }
    }

    public static class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;

        public Properties() {
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getThumbnail_image() {
            return thumbnail_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }

        @Override
        public String toString() {
            return "Properties{" +
                "nickname='" + nickname + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", thumbnail_image='" + thumbnail_image + '\'' +
                '}';
        }
    }

}