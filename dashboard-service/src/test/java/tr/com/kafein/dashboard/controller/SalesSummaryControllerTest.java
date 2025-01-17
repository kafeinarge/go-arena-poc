package tr.com.kafein.dashboard.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import tr.com.kafein.dashboard.mapper.SalesSummaryMapper;
import tr.com.kafein.dashboard.service.SalesSummaryService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tr.com.kafein.dashboard.TestConstants.CATEGORY;
import static tr.com.kafein.dashboard.TestConstants.DIRECTION;
import static tr.com.kafein.dashboard.TestConstants.MONTH;
import static tr.com.kafein.dashboard.TestConstants.PAGEABLE;
import static tr.com.kafein.dashboard.TestConstants.PAGE_NO;
import static tr.com.kafein.dashboard.TestConstants.PAGE_SIZE;
import static tr.com.kafein.dashboard.TestConstants.SORT_BY;
import static tr.com.kafein.dashboard.TestConstants.YEAR;

class SalesSummaryControllerTest {
    private SalesSummaryController controller;
    private SalesSummaryService mockService;

    @BeforeEach
    void setUp() {
        mockService = mock(SalesSummaryService.class);
        SalesSummaryMapper mockMapper = mock(SalesSummaryMapper.class);
        controller = new SalesSummaryController(mockService, mockMapper);
    }

    @Test
    void findPaginated_whenParamsIsGiven_thenReturnEquivalentPage() {
        final Page mockPage = Page.empty(PAGEABLE);
        when(mockService.getSummaries(YEAR, MONTH, CATEGORY, PAGEABLE)).thenReturn(mockPage);

        Page result = controller.findPaginated(PAGE_NO, PAGE_SIZE, SORT_BY, DIRECTION, YEAR, MONTH, CATEGORY);

        Assertions.assertEquals(mockPage, result);
    }
}
