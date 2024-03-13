package com.example.demo.common.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoginController {

    public String login() {
        return "login";
    }
}
