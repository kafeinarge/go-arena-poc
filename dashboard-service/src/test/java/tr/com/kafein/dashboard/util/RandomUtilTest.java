package tr.com.kafein.dashboard.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomUtilTest {

    @Test
    void getRandomBetween() {
        double high = 2.0;
        double low = 1.0;
        double mockRandom = 0;

        Integer result = RandomUtil.getRandomBetween(high, low);
        int expectedResult = (int)((mockRandom*(high-low)) + low);
        Assertions.assertEquals(expectedResult, result);
    }
}
