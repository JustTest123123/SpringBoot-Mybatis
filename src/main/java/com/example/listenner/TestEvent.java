package com.example.listenner;

import com.example.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/30 下午8:55
 */
public class TestEvent extends ApplicationEvent {
    User user;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(Object source, User user) {
        super(source);
        this.user = user;
        System.out.println(user);
    }

}
    