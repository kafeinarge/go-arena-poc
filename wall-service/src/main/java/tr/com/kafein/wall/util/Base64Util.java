package tr.com.kafein.wall.util;

import org.springframework.web.multipart.MultipartFile;
import tr.com.kafein.wall.exception.InternalServerError;
import tr.com.kafein.wall.exception.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import static tr.com.kafein.wall.util.Constants.IMAGE_NOT_FOUND_MSG;
import static tr.com.kafein.wall.util.Constants.IMAGE_NOT_READABLE;

public final class Base64Util {
    private Base64Util() {
        throw new IllegalStateException("Util class");
    }

    public static String toBase64(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (FileNotFoundException e) {
            throw new NotFoundException(IMAGE_NOT_FOUND_MSG);
        } catch (IOException ioe) {
            throw new InternalServerError(IMAGE_NOT_READABLE);
        }
    }
}
