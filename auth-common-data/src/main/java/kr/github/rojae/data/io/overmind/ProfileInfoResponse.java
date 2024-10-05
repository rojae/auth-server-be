package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileInfoResponse {
    private String name;
    private String email;
    private String platformType;
    private String profileImage;
    private boolean isAuth;
    private boolean isEnable;
    private String lastLoginDate;
}
