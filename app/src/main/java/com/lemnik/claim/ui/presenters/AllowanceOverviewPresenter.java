package com.lemnik.claim.ui.presenters;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.util.Pair;

import com.lemnik.claim.model.Allowance;
import com.lemnik.claim.util.ActionCommand;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AllowanceOverviewPresenter {

    public final ObservableField<SpendingStats> spendingStats = new ObservableField<>();

    public final Allowance allowance;

    private final UpdateSpendStatsCommand updateSpendStatsCommand = new UpdateSpendStatsCommand();

    private final Observable.OnPropertyChangedCallback allowanceObserver = new Observable.OnPropertyChangedCallback() {

        @Override
        public void onPropertyChanged(
                final Observable observable,
                final int propertyId) {
            updateSpendStatsCommand.exec(allowance);
        }
    };

    public AllowanceOverviewPresenter(final Allowance allowance) {
        this.allowance = allowance;
        this.allowance.addOnPropertyChangedCallback(allowanceObserver);
    }

    public void detach() {
        allowance.removeOnPropertyChangedCallback(allowanceObserver);
    }

    public void updateAllowance(final CharSequence newAllowance) {
        try {
            allowance.setAmountPerDay(Integer.parseInt(newAllowance.toString()));
        } catch (final RuntimeException ex) {
            //ignore
            allowance.setAmountPerDay(0);
        }
    }

    public static class SpendingStats {

        public final int total;
        public final int today;
        public final int thisWeek;

        SpendingStats(
                final int total,
                final int today,
                final int thisWeek) {

            this.total = total;
            this.today = today;
            this.thisWeek = thisWeek;
        }
    }

    private class UpdateSpendStatsCommand extends ActionCommand<Allowance, SpendingStats> {

        Pair<Date, Date> getThisWeek() {
            final GregorianCalendar today = new GregorianCalendar();
            today.set(Calendar.HOUR_OF_DAY, today.getActualMaximum(Calendar.HOUR_OF_DAY));
            today.set(Calendar.MINUTE, today.getActualMaximum(Calendar.MINUTE));
            today.set(Calendar.SECOND, today.getActualMaximum(Calendar.SECOND));
            today.set(Calendar.MILLISECOND, today.getActualMaximum(Calendar.MILLISECOND));
            final Date end = today.getTime();

            today.add(Calendar.DATE, -(today.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY));
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            return new Pair<>(today.getTime(), end);
        }

        Pair<Date, Date> getToday() {
            final GregorianCalendar today = new GregorianCalendar();
            today.set(Calendar.HOUR_OF_DAY, today.getActualMaximum(Calendar.HOUR_OF_DAY));
            today.set(Calendar.MINUTE, today.getActualMaximum(Calendar.MINUTE));
            today.set(Calendar.SECOND, today.getActualMaximum(Calendar.SECOND));
            today.set(Calendar.MILLISECOND, today.getActualMaximum(Calendar.MILLISECOND));
            final Date end = today.getTime();

            today.add(Calendar.DATE, -1);
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            return new Pair<>(today.getTime(), end);
        }

        @Override
        public SpendingStats onBackground(final Allowance allowance) throws Exception {
            final Pair<Date, Date> today = getToday();
            final Pair<Date, Date> thisWeek = getThisWeek();

            // for stats we round everything to integers
            return new SpendingStats(
                    (int) allowance.getTotalSpent(),
                    (int) allowance.getAmountSpent(today.first, today.second),
                    (int) allowance.getAmountSpent(thisWeek.first, thisWeek.second)
            );
        }

        @Override
        public void onForeground(final SpendingStats newStats) {
            spendingStats.set(newStats);
        }
    }

}
