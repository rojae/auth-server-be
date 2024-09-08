package kr.github.rojae.data.io.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiLoginRequest {
    private String email;
    private String password;
    private String platformType;
}
