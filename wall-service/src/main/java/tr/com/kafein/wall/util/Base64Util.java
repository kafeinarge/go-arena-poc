package tr.com.kafein.wall.util;

import org.springframework.web.multipart.MultipartFile;
import tr.com.kafein.wall.exception.InternalServerError;
import tr.com.kafein.wall.exception.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class Base64Util {
    public static String toBase64(MultipartFile file) {
        try {
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (FileNotFoundException e) {
            throw new NotFoundException("Resim Bulunamadı");
        } catch (IOException ioe) {
            throw new InternalServerError("Resim okunurken hata meydana geldi");
        }
    }
}
