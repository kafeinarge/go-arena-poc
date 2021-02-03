package tr.com.kafein.dashboard.util;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String TOKEN_AUTHORITIES_KEY = "authorities";
    public static final String TOKEN_SECRET = "venti_Hr_venti_Hr";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String SESSION_EXPIRE_MSG = "Oturumunuzun süresi dolmuş, lütfen tekrar giriş yapınız";
    public static final String MUST_BE_LOGIN = "Lütfen Giris Yapınız.";
}
