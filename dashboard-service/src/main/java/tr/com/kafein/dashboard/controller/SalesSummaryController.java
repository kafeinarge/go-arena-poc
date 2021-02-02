package tr.com.kafein.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kafein.dashboard.dto.SalesSummaryDto;
import tr.com.kafein.dashboard.mapper.SalesSummaryMapper;
import tr.com.kafein.dashboard.service.SalesSummaryService;
import tr.com.kafein.dashboard.type.SalesCategoryType;


@RestController
public class SalesSummaryController {

    private final SalesSummaryService salesSummaryService;
    private final SalesSummaryMapper mapper;

    public SalesSummaryController(SalesSummaryService salesSummaryService,
                                  SalesSummaryMapper mapper) {
        this.salesSummaryService = salesSummaryService;
        this.mapper = mapper;
    }

    @GetMapping("/summaries")
    public Page<SalesSummaryDto> findPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction,
                                               @RequestParam(name = "year", required = false) Integer year,
                                               @RequestParam(name = "month", required = false) Integer month,
                                               @RequestParam(name = "category", required = false) SalesCategoryType category) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.valueOf(direction), sortBy);
        return salesSummaryService.getSummaries(year, month, category, pageable).map(mapper::toDto);
    }
}
