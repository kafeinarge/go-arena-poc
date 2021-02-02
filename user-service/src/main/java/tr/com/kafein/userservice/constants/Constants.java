package tr.com.kafein.userservice.constants;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class.");
    }

    public static long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_AUTHORITIES_KEY = "authorities";
    public static final String TOKEN_SECRET = "venti_Hr_venti_Hr";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String USER_NOT_FOUND_MSG = "User id :[%s] could not found";
}
