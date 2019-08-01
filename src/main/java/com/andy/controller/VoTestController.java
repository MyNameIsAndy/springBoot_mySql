package com.andy.controller;

import com.andy.vo.StudentVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoTestController {
    @RequestMapping("test-vo")
    public void test(){
       StudentVo studentVo = StudentVo.builder()
               .age("1")
               .home("磁县")
               .email("chaoge.com")
               .name("超哥")
               .build();

        System.out.println(studentVo.toString());
        studentVo = StudentVo.builder().build();
        System.out.println("清空："+studentVo.toString());

    }
}
