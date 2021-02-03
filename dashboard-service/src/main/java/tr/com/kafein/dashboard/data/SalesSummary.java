package tr.com.kafein.dashboard.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tr.com.kafein.dashboard.dto.UserDto;
import tr.com.kafein.dashboard.type.SalesCategoryType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "sales_summary", schema = "dashboardservice")
public class SalesSummary {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @Transient
    private UserDto user;

    private Integer paidCount;

    private Integer unpaidCount;

    private Integer totalGoal;

    private Integer year;

    private Integer month;

    @Enumerated(EnumType.STRING)
    private SalesCategoryType category;

    public SalesSummary(Integer year, Integer month, SalesCategoryType category) {
        this.year = year;
        this.month = month;
        this.category = category;
    }

    public SalesSummary(Long userId, Integer paidCount, Integer unpaidCount, Integer totalGoal, Integer year, Integer month, SalesCategoryType category) {
        this.userId = userId;
        this.paidCount = paidCount;
        this.unpaidCount = unpaidCount;
        this.totalGoal = totalGoal;
        this.year = year;
        this.month = month;
        this.category = category;
    }
}
