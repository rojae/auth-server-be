package io.github.rojae.authunionapi.api.coreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoreApiCheckDuplicateEmail {
    private String email;
}
