package tr.com.kafein.wall.util;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class.");
    }

    public static long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_AUTHORITIES_KEY = "authorities";
    public static final String TOKEN_SECRET = "venti_Hr_venti_Hr";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String SESSION_EXPIRE_MSG = "Oturumunuzun süresi dolmuş, lütfen tekrar giriş yapınız";
    public static final String TYPE_MISMATCH_MSG = "Bu endpoint için %s değeri '%s' gönderilmiştir ancak beklenilen tip %s dır;";
    public static final String POST_NOT_FOUND_MSG = "Post id :[ %s ] bulunamadı";
    public static final String ADMIN_PERMISSION_MSG = "Bu işlemi sadece adminler gerçekleştirebilir";
    public static final String IMAGE_NOT_FOUND_MSG = "Resim Bulunamadı";
    public static final String IMAGE_NOT_READABLE = "Resim okunurken hata meydana geldi";
}
