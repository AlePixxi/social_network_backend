package com.social.service;

import com.social.dto.UserDTO;

import java.util.Map;

public interface LoginService {

    public Map<String, String> login(UserDTO request);

}
