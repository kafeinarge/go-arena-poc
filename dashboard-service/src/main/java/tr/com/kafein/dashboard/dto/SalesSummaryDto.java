package tr.com.kafein.dashboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tr.com.kafein.dashboard.type.SalesCategoryType;

@Data
@EqualsAndHashCode
@ToString
public class SalesSummaryDto {
    private UserDto user;

    private Integer paidCount;

    private Integer unpaidCount;

    private Integer totalGoal;

    private int year;

    private int month;

    private SalesCategoryType category;
}
