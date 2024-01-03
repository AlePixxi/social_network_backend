package com.social.service;

import com.social.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {

    public ResponseEntity<Map<String, String>> login(UserDTO request);

}
