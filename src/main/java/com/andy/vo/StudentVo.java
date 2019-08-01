package com.andy.vo;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class StudentVo {
    String name;
    String age;
    String home;
    String email;
}
