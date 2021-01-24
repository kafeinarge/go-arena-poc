package tr.com.kafein.dashboard.data;

import tr.com.kafein.dashboard.dto.UserDto;
import tr.com.kafein.dashboard.type.SalesCategoryType;

import javax.persistence.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(Integer paidCount) {
        this.paidCount = paidCount;
    }

    public Integer getUnpaidCount() {
        return unpaidCount;
    }

    public void setUnpaidCount(Integer unpaidCount) {
        this.unpaidCount = unpaidCount;
    }

    public Integer getTotalGoal() {
        return totalGoal;
    }

    public void setTotalGoal(Integer totalGoal) {
        this.totalGoal = totalGoal;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public SalesCategoryType getCategory() {
        return category;
    }

    public void setCategory(SalesCategoryType category) {
        this.category = category;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
