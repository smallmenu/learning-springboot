package com.niuchaoqun.springboot.security.dto.data;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Logined {
    private Long id;

    private String username;

    private String name;
}
