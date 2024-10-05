package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckDuplicateEmail {
    @NotBlank(message = "email은 빈 칸을 가질 수 없습니다")
    private String email;
}
