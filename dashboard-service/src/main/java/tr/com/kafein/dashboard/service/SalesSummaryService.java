package tr.com.kafein.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.kafein.dashboard.accessor.UserServiceAccessor;
import tr.com.kafein.dashboard.data.SalesSummary;
import tr.com.kafein.dashboard.dto.UserDto;
import tr.com.kafein.dashboard.repository.SalesSummaryRepository;
import tr.com.kafein.dashboard.type.SalesCategoryType;

import javax.annotation.PostConstruct;

import static tr.com.kafein.dashboard.util.RandomUtil.getRandomBetween;

@Service
public class SalesSummaryService {

    @Autowired
    private SalesSummaryRepository salesSummaryRepository;

    @Autowired
    private UserServiceAccessor userServiceAccessor;

    public Page<SalesSummary> getSummaries(Integer year, Integer month, SalesCategoryType category, Pageable pageable) {
        SalesSummary filterObject = new SalesSummary();
        filterObject.setYear(year);
        filterObject.setMonth(month);
        filterObject.setCategory(category);
        Page<SalesSummary> result = salesSummaryRepository.findAll(Example.of(filterObject), pageable);
        if (result.getNumberOfElements() > 0) {
            final UserDto[] user = {null};
            result.get().forEach(salesSummary -> {
                if(user[0] == null) {
                    user[0] = userServiceAccessor.getById(salesSummary.getUserId());
                }
                salesSummary.setUser(user[0]);
            });
        }

        return result;
    }

    @PostConstruct
    private void createDummySummaries() {
        UserDto randomUser = userServiceAccessor.getOne();
        if (salesSummaryRepository.findAll().size() == 0) {
            for (int i = 1; i < 13; i++) {
                for (int j = 0; j < 3; j++) {
                    createDummySummary(randomUser, SalesCategoryType.values()[j], 2020, i);
                }
            }
        }
    }

    private void createDummySummary(UserDto user, SalesCategoryType category, int year, int month) {
        SalesSummary summary = new SalesSummary();
        summary.setYear(year);
        summary.setMonth(month);
        summary.setUserId(user.id);
        summary.setCategory(category);
        summary.setPaidCount(getRandomBetween(1, 50));
        summary.setUnpaidCount(getRandomBetween(1, 50));
        summary.setTotalGoal(getRandomBetween(1, 100));

        salesSummaryRepository.save(summary);
    }
}