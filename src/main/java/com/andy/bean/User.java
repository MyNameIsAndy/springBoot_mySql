package com.andy.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by admin on 2019-01-11.
 */
@Table(name = "users")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String houseAddress;
    private String phone;
    private String email;
}
