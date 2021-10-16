package com.authentication.Authentication.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class SHAEncodingUtil {

    public static SHAEncodingUtil singleInstance = null;

    private String data;

    private SHAEncodingUtil() {

    }

    public static SHAEncodingUtil getInstance(){
        if(singleInstance == null)
            singleInstance = new SHAEncodingUtil();
        return singleInstance;
    }

    public String getSHAHash(String data) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        md.update(data.getBytes(StandardCharsets.UTF_8));

        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }
}
