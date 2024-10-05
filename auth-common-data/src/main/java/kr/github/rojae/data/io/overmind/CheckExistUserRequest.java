package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckExistUserRequest {
    private String email;
    private String platformType;
}