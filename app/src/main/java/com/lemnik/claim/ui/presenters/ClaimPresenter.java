package com.lemnik.claim.ui.presenters;

/**
 * Created by jason on 2017/08/01.
 */
public class ClaimPresenter {

    public String formatAmount(final double amount) {
        return amount == 0
                ? ""
                : amount == (int) amount
                ? Integer.toString((int) amount)
                : String.format("%.2f", amount);
    }

}
