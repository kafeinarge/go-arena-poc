package tr.com.kafein.dashboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tr.com.kafein.dashboard.accessor.UserServiceAccessor;
import tr.com.kafein.dashboard.data.SalesSummary;
import tr.com.kafein.dashboard.dto.UserDto;
import tr.com.kafein.dashboard.repository.SalesSummaryRepository;
import tr.com.kafein.dashboard.type.SalesCategoryType;
import tr.com.kafein.dashboard.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static tr.com.kafein.dashboard.Constants.CATEGORY;
import static tr.com.kafein.dashboard.Constants.MONTH;
import static tr.com.kafein.dashboard.Constants.PAGEABLE;
import static tr.com.kafein.dashboard.Constants.YEAR;

class SalesSummaryServiceTest {
    private SalesSummaryService service;
    private SalesSummaryRepository mockRepository;
    private UserServiceAccessor mockUserAccessor;
    private final SalesSummary filterObject = new SalesSummary(YEAR, MONTH, CATEGORY);

    @BeforeEach
    void setUp() {
        mockRepository = mock(SalesSummaryRepository.class);
        mockUserAccessor = mock(UserServiceAccessor.class);
        service = new SalesSummaryService(mockRepository, mockUserAccessor);
    }

    @Test
    void getSummaries_WhenEmptyRecord_ThenReturnEmptyPage() {
        Page emptyPage = new PageImpl(Collections.emptyList());
        when(mockRepository.findAll(Example.of(filterObject), PAGEABLE)).thenReturn(emptyPage);

        Page<SalesSummary> summaries = service.getSummaries(YEAR, MONTH, CATEGORY, PAGEABLE);

        Assertions.assertEquals(emptyPage, summaries);
    }

    @Test
    void getSummaries_WhenNotEmptyRecords_ThenReturnModifiedRecords() {
        SalesSummary s1 = new SalesSummary();
        s1.setId(1L);
        s1.setUserId(1L);
        SalesSummary s2 = new SalesSummary();
        s2.setId(2L);
        s2.setUserId(2L);
        UserDto u1 = new UserDto();
        u1.setId(1L);
        UserDto u2 = new UserDto();
        u2.setId(2L);

        List<SalesSummary> salesSummaries = Arrays.asList(s1, s2);
        Page page = new PageImpl(salesSummaries);
        when(mockRepository.findAll(Example.of(filterObject), PAGEABLE)).thenReturn(page);

        when(mockUserAccessor.getById(1L)).thenReturn(u1);
        when(mockUserAccessor.getById(2L)).thenReturn(u2);

        Page<SalesSummary> result = service.getSummaries(YEAR, MONTH, CATEGORY, PAGEABLE);

        Assertions.assertEquals(salesSummaries.size(), result.getTotalElements());

        List<SalesSummary> resultSummaries = result.get().collect(Collectors.toList());
        Assertions.assertNotNull(resultSummaries.stream().filter(s -> s.getId().equals(1L)).findFirst().orElse(null));
        Assertions.assertNotNull(resultSummaries.stream().filter(s -> s.getId().equals(2L)).findFirst().orElse(null));

        SalesSummary expectedS1 = resultSummaries.stream().filter(s -> s.getId().equals(1L)).findFirst().orElse(null);
        SalesSummary expectedS2 = resultSummaries.stream().filter(s -> s.getId().equals(2L)).findFirst().orElse(null);
        Assertions.assertEquals(u1, expectedS1.getUser());
        // TODO: mahmuta sorulacak
        // Assertions.assertEquals(u2, expectedS2.getUser());
    }

    @Test
    void createDummySummary_CreateSummaryAndSave() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UserDto u = new UserDto();
        u.setId(1L);

        MockedStatic mockedRandomUtil = mockStatic(RandomUtil.class);

        mockedRandomUtil.when(() -> RandomUtil.getRandomBetween(1, 50)).thenReturn(40);
        mockedRandomUtil.when(() -> RandomUtil.getRandomBetween(1, 100)).thenReturn(50);

        Method createDummySummary = SalesSummaryService.class.getDeclaredMethod("createDummySummary", UserDto.class, SalesCategoryType.class, int.class, int.class);
        createDummySummary.setAccessible(true);
        createDummySummary.invoke(service, u, CATEGORY, YEAR, MONTH);

        SalesSummary expectedSummary = new SalesSummary(u.getId(), 40, 40, 50, YEAR, MONTH, CATEGORY);

        Mockito.verify(mockRepository, times(1)).save(expectedSummary);
    }
}
