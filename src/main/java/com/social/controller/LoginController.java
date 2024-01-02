package com.social.controller;

import com.social.dto.UserDTO;
import com.social.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("rest/api/user")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> utenteHRLogin(@RequestBody UserDTO userDTO)  {
        return ResponseEntity.ok(loginService.login(userDTO));
    }

}
