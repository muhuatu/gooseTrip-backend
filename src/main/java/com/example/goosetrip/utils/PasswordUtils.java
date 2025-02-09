package com.example.goosetrip.utils;

import com.example.goosetrip.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    @Autowired
    private static UserDao userDao;

    // 加密：回傳加密後的密碼
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 解密：核對 使用者輸入密碼 VS 資料庫密碼
    public static boolean verifyPassword(String inputPwd, String DBPwd) {
        return BCrypt.checkpw(inputPwd, DBPwd);
    }

}
