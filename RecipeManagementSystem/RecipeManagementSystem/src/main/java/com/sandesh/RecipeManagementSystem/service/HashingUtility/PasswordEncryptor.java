package com.sandesh.RecipeManagementSystem.service.HashingUtility;

import jakarta.xml.bind.DatatypeConverter;

import javax.mail.Message;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    public static String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(userPassword.getBytes());
        byte[] digest = md5.digest();

         return DatatypeConverter.printHexBinary(digest);
    }
}
