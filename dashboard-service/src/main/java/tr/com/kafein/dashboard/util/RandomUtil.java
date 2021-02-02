package tr.com.kafein.dashboard.util;


public final class RandomUtil {
    private RandomUtil() {
        throw new IllegalStateException("Constants class");
    }

    public static Integer getRandomBetween(double low, double high) {
        return (int) ((Math.random() * (high - low)) + low);
    }
}
