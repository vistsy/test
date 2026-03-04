package com.example.report;

import java.util.List;

public interface SvcContactsRepository {
    List<SvcContacts> findByBillingNoContainingAndIsDeleted(String billingNo, String isDeleted);
}
