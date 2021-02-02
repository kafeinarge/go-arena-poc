package tr.com.kafein.uaaserver.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void getNthDay() {
        MockedStatic<Calendar> calendarMockedStatic = Mockito.mockStatic(Calendar.class);

        Date expectedDate = new Date();
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        Mockito.when(mockCalendar.getTime()).thenReturn(expectedDate);
        calendarMockedStatic.when(Calendar::getInstance).thenReturn(mockCalendar);

        Date result = DateUtil.getNthDay(5);

        Assertions.assertEquals(expectedDate, result);

        calendarMockedStatic.close();
    }
}
