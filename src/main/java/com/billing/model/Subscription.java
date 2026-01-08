package com.billing.model;

import java.time.LocalDate;

public class Subscription {
    private String userName;
    private Plan plan;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private boolean autoRenewal;
    
    public Subscription(String userName, Plan plan){
        this.userName = userName;
        this.plan = plan;
        this.startDate = LocalDate.now();
        this.expiryDate = calculateExpiryDate();
        this.autoRenewal = false;
    }
    
    private LocalDate calculateExpiryDate() {
        return calculateExpiryDate(startDate);
    }

    private LocalDate calculateExpiryDate(LocalDate baseDate) {
        if (plan instanceof MonthlyPlan) {
            return baseDate.plusMonths(1);
        } else if (plan instanceof AnnualPlan) {
            return baseDate.plusYears(1);
        }
        return baseDate.plusMonths(1); // default
    }
    
    public String getUserName(){
        return userName;
    }
    
    public double getAmount(){
        return plan.calculateAmount();
    }
    
    public Plan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public boolean isAutoRenewal() {
        return autoRenewal;
    }
    
    public void setAutoRenewal(boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public boolean isExpiringSoon(int daysThreshold) {
        LocalDate threshold = LocalDate.now().plusDays(daysThreshold);
        return expiryDate.isBefore(threshold) && !isExpired();
    }
    
    public void renew() {
        LocalDate renewalBase = LocalDate.now().isAfter(expiryDate) ? LocalDate.now() : expiryDate;
        this.expiryDate = calculateExpiryDate(renewalBase);
    }
    
    public void changePlan(Plan newPlan) {
        // Switch plan without touching dates; renewal will recalc expiry based on the new plan.
        this.plan = newPlan;
    }
}