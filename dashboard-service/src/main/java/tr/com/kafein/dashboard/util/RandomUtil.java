package tr.com.kafein.dashboard.util;


public class RandomUtil {
    public static Integer getRandomBetween(double low, double high) {
        return (int) ((Math.random() * (high - low)) + low);
    }
}
