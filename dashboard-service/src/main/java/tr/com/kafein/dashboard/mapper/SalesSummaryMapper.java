package tr.com.kafein.dashboard.mapper;

import org.mapstruct.Mapper;
import tr.com.kafein.dashboard.data.SalesSummary;
import tr.com.kafein.dashboard.dto.SalesSummaryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesSummaryMapper {
    SalesSummaryDto toDto(SalesSummary entity);
    List<SalesSummaryDto> toDto(List<SalesSummary> entities);
}
