package com.example.demo.src.subscription.model;

import com.example.demo.src.user.entity.User;
import lombok.Getter;

@Getter
public class UserDeletedEvent {
    private User user;
    public UserDeletedEvent(User user) {
        this.user = user;
    }
}
