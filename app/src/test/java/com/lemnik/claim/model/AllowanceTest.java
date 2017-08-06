package com.lemnik.claim.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by jason on 2017/08/02.
 */

public class AllowanceTest {

    Allowance allowance;

    @Before
    public void createTestAllowance() {
        allowance = new Allowance(250);

        allowance.addClaimItem(new ClaimItem("Taxi", 80, new Date(116, 02, 02), Category.TRANSPORT));
        allowance.addClaimItem(new ClaimItem("Lunch", 25, new Date(116, 02, 02), Category.FOOD));
        allowance.addClaimItem(new ClaimItem("Dinner", 100, new Date(116, 02, 02), Category.FOOD));
        allowance.addClaimItem(new ClaimItem("Breakfast", 50, new Date(116, 02, 03), Category.FOOD));
        allowance.addClaimItem(new ClaimItem("Stationary", 55, new Date(116, 02, 03), Category.BUSINESS));
        allowance.addClaimItem(new ClaimItem("Movie", 150, new Date(116, 02, 04), Category.ENTERTAINMENT));
    }

    @Test
    public void testClaimItemOrder() {
        assertEquals("startDate", new Date(116, 02, 02), allowance.getStartDate());
        assertEquals("endDate", new Date(116, 02, 04), allowance.getEndDate());
    }

    @Test
    public void testTotalSpend() {
        assertEquals(460.0, allowance.getTotalSpent(), 0.001);
    }

}
