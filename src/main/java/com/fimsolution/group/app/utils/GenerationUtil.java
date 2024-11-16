package com.fimsolution.group.app.utils;

import java.util.Random;
import java.util.UUID;

public class GenerationUtil {
    public static String uuidGenerate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


    public static String generateUniqueId() {
        return UUID.randomUUID().toString(); // Generate a unique UUID and convert it to a String
    }


}
