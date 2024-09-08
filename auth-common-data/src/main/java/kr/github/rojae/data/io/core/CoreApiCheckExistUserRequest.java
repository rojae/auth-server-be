package kr.github.rojae.data.io.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiCheckExistUserRequest {
    private String email;
    private String platformType;
}
