package com.example.yoloq.service;

public interface PasswordService {
    String encodePassword(String password);
    boolean checkPassword(String enteredPassword, String encodedPassword);
}
