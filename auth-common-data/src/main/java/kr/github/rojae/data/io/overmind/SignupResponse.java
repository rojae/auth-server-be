package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private String email;
    private String name;
    private String platformType;
}
