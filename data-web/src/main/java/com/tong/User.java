package com.tong;

import lombok.Data;

import java.util.Date;
/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: User
 * @time: 2019/7/11 11:43
 * @desc:
 */
@Data
public class User {

    private int id;
    private String username;
    private int age;
    private Date ctm;

}