package tr.com.kafein.dashboard.dto;

import tr.com.kafein.dashboard.type.SalesCategoryType;

public class SalesSummaryDto {
    private UserDto user;

    private Integer paidCount;

    private Integer unpaidCount;

    private Integer totalGoal;

    private int year;

    private int month;

    private SalesCategoryType category;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public SalesCategoryType getCategory() {
        return category;
    }

    public void setCategory(SalesCategoryType category) {
        this.category = category;
    }
}
