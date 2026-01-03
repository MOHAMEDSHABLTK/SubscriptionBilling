package com.billing.service;

import java.util.List;

import com.billing.exception.InvalidPaymentException;
import com.billing.model.Subscription;

public class RenewalService {
    private PaymentService paymentService;
    private BillingService billingService;
    
    public RenewalService() {
        this.paymentService = new PaymentService();
        this.billingService = new BillingService();
    }
    
    public void processRenewal(Subscription subscription) throws InvalidPaymentException {
        if (subscription.isExpired()) {
            System.out.println("Processing renewal for expired subscription: " + subscription.getUserName());
        } else {
            System.out.println("Processing early renewal for: " + subscription.getUserName());
        }
        
        double amount = subscription.getAmount();
        paymentService.validatePayment(amount);
        
        subscription.renew();
        billingService.generateBill(subscription);
        
        System.out.println("Subscription renewed successfully until: " + subscription.getExpiryDate());
    }
    
    public void enableAutoRenewal(Subscription subscription) {
        subscription.setAutoRenewal(true);
        System.out.println("Auto-renewal enabled for " + subscription.getUserName());
    }
    
    public void disableAutoRenewal(Subscription subscription) {
        subscription.setAutoRenewal(false);
        System.out.println("Auto-renewal disabled for " + subscription.getUserName());
    }
    
    public void processAutoRenewals(List<Subscription> subscriptions) {
        System.out.println("Checking for auto-renewals...");
        for (Subscription subscription : subscriptions) {
            if (subscription.isAutoRenewal() && subscription.isExpired()) {
                try {
                    processRenewal(subscription);
                } catch (InvalidPaymentException e) {
                    System.err.println("Auto-renewal failed for " + subscription.getUserName() + ": " + e.getMessage());
                }
            }
        }
    }
    
    public void sendRenewalReminders(List<Subscription> subscriptions, int daysBeforeExpiry) {
        System.out.println("Checking for subscriptions expiring soon...");
        for (Subscription subscription : subscriptions) {
            if (subscription.isExpiringSoon(daysBeforeExpiry)) {
                System.out.println("REMINDER: Subscription for " + subscription.getUserName() + 
                                 " expires on " + subscription.getExpiryDate());
            }
        }
    }
}