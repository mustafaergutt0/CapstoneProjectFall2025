package com.ByteCraft.IotProject.Dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
