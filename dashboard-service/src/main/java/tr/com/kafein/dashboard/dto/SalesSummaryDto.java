package tr.com.kafein.dashboard.dto;

import tr.com.kafein.dashboard.type.SalesCategoryType;

public class SalesSummaryDto {
    private UserDto user;

    private Double paidCount;

    private Double unpaidCount;

    private Double totalGoal;

    private int year;

    private int month;

    private SalesCategoryType category;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Double getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(Double paidCount) {
        this.paidCount = paidCount;
    }

    public Double getUnpaidCount() {
        return unpaidCount;
    }

    public void setUnpaidCount(Double unpaidCount) {
        this.unpaidCount = unpaidCount;
    }

    public Double getTotalGoal() {
        return totalGoal;
    }

    public void setTotalGoal(Double totalGoal) {
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
