package kr.github.rojae.data.io.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiSignupResponse {
    private String email;
    private String name;
    private String platformType;
}
