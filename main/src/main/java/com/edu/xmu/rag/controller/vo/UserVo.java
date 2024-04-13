package com.edu.xmu.rag.controller.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    @NotBlank(message = "用户名不能为空")
    String name;
    @NotBlank(message = "密码不能为空")
    String password;
}
