package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckDuplicateNickname {
    @NotBlank(message = "nickname은 빈칸을 가질 수 없습니다")
    private String nickname;
}
