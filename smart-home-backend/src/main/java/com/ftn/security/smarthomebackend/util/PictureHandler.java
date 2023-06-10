package com.ftn.security.smarthomebackend.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static com.ftn.security.smarthomebackend.util.Constants.PHOTOS_FILE_PATH;

public class PictureHandler {

    private static String generateBase64String(final byte[] pictureData) {

        return Base64.getEncoder().encodeToString(pictureData);
    }

    public static String generatePhotoPath(final String name) {

        return PHOTOS_FILE_PATH + name;
    }

    public static byte[] getPictureDataByName(final String name) throws IOException
    {
        return Files.readAllBytes(Paths.get(generatePhotoPath(name)));
    }

    public static String convertPictureToBase64ByName(final String name) {
        try {
            byte[] pictureData = new byte[0];
            pictureData = getPictureDataByName(name);

            return generateBase64String(pictureData);
        } catch (IOException e) {
            return "";
        }
    }

}
