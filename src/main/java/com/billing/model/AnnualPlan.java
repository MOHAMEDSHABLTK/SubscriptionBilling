package com.billing.model;

public class AnnualPlan extends Plan {
    public AnnualPlan(double price){
        super(price);
    }
    @Override
    public double calculateAmount(){
        return price * 12 * 0.9;
    }
    @Override
    public String getPlanName() {
        return "Annual Plan";
    }

    @Override
    public String getDuration() {
        return "12 Months";
    }
}
