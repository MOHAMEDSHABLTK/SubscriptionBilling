package com.billing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.billing.model.Subscription;

public class SubscriptionService {
    private List<Subscription> subscriptions = new ArrayList<>();
    
    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptions;
    }
    
    public List<Subscription> getActiveSubscriptions() {
        return subscriptions.stream()
                .filter(s -> !s.isExpired())
                .collect(Collectors.toList());
    }
    
    public List<Subscription> getExpiredSubscriptions() {
        return subscriptions.stream()
                .filter(Subscription::isExpired)
                .collect(Collectors.toList());
    }
    
    public List<Subscription> getAutoRenewalSubscriptions() {
        return subscriptions.stream()
                .filter(Subscription::isAutoRenewal)
                .collect(Collectors.toList());
    }
    
    public Subscription findSubscriptionByUser(String userName) {
        return subscriptions.stream()
                .filter(s -> s.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }
}