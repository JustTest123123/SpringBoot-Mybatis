package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/21 下午8:59
 */
@Configuration
public class Test {
    @Bean
    public Con con () {
        return new Con();
    }

}
    