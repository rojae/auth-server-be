package kr.github.rojae.data.io.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoreApiLoginHistoryRequest {
    private String email;
    private String platformType;
}
