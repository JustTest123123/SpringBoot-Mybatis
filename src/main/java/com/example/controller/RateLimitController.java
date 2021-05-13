package com.example.controller;

import com.example.controller.anno.LimitAnno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/19 下午3:59
 */
@RestController
public class RateLimitController {
    @RequestMapping("/limit")
    @LimitAnno(value = "success")
    public Object success() {
        return new Success("成功",200);
    }

}
@AllArgsConstructor
@NoArgsConstructor
@Data
class Success{
    String message;
    int code;
}
    