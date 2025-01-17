package tr.com.kafein.dashboard;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tr.com.kafein.dashboard.type.SalesCategoryType;

public class TestConstants {
    private TestConstants() {
        throw new IllegalStateException("Constants class.");
    }

    public static final String MOCK_STRING = "tests";
    public static final String ROLE1 = "ROLE1";
    public static final String ROLE2 = "ROLE2";

    public static final Integer PAGE_NO = 0;
    public static final Integer PAGE_SIZE = 10;
    public static final String SORT_BY = "id";
    public static final String DIRECTION = "ASC";
    public static final Integer YEAR = 2000;
    public static final Integer MONTH = 6;
    public static final SalesCategoryType CATEGORY = SalesCategoryType.CATEGORY1;
    public static final Pageable PAGEABLE = PageRequest.of(PAGE_NO, PAGE_SIZE, Sort.Direction.valueOf(DIRECTION), SORT_BY);
}
