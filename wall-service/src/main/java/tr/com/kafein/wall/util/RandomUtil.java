package tr.com.kafein.wall.util;


public class RandomUtil {
    public static Integer getRandomBetween(double low, double high) {
        return (int) ((Math.random() * (high - low)) + low);
    }
}
