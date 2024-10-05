package kr.github.rojae.data.io.smtp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDto {
    private String email;
    private String mailType;
}
