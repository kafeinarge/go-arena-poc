package tr.com.kafein.dashboard.data;

import tr.com.kafein.dashboard.dto.UserDto;
import tr.com.kafein.dashboard.type.SalesCategoryType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

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

    public SalesSummary() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesSummary that = (SalesSummary) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(user, that.user) &&
                Objects.equals(paidCount, that.paidCount) &&
                Objects.equals(unpaidCount, that.unpaidCount) &&
                Objects.equals(totalGoal, that.totalGoal) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, user, paidCount, unpaidCount, totalGoal, year, month, category);
    }
}
