package com.lemnik.claim.ui.presenters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Pair;

import com.lemnik.claim.ClaimApplication;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.util.ActionCommand;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AllowanceOverviewPresenter {

    public final ObservableField<SpendingStats> spendingStats = new ObservableField<>();
    public final ObservableInt allowance = new ObservableInt();
    private final UpdateSpendingStatsCommand updateSpendStatsCommand = new UpdateSpendingStatsCommand();

    public AllowanceOverviewPresenter(
            final LifecycleOwner lifecycleOwner,
            final int allowance) {

        ClaimApplication.getClaimDatabase()
                .claimItemDao()
                .selectAll()
                .observe(lifecycleOwner, new Observer<List<ClaimItem>>() {
                    @Override
                    public void onChanged(final List<ClaimItem> claimItems) {
                        updateSpendStatsCommand.exec(claimItems);
                    }
                });

        this.allowance.set(allowance);
    }

    public void updateAllowance(final CharSequence newAllowance) {
        try {
            allowance.set(Integer.parseInt(newAllowance.toString()));
        } catch (final RuntimeException ex) {
            //ignore
            allowance.set(0);
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

    private class UpdateSpendingStatsCommand extends ActionCommand<List<ClaimItem>, SpendingStats> {

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

            return new Pair<>(today.getTime(), end);
        }

        @Override
        public SpendingStats onBackground(final List<ClaimItem> items) throws Exception {
            final Pair<Date, Date> today = getToday();
            final Pair<Date, Date> thisWeek = getThisWeek();

            double spentTotal = 0;
            double spentToday = 0;
            double spentThisWeek = 0;

            for (int i = 0; i < items.size(); i++) {
                final ClaimItem item = items.get(i);
                spentTotal += item.amount;

                if (item.getTimestamp().compareTo(thisWeek.first) >= 0
                        && item.getTimestamp().compareTo(thisWeek.second) <= 0) {

                    spentThisWeek += item.amount;
                }

                if (item.getTimestamp().compareTo(today.first) >= 0
                        && item.getTimestamp().compareTo(today.second) <= 0) {

                    spentToday += item.amount;
                }
            }

            // for stats we round everything to integers
            return new SpendingStats(
                    (int) spentTotal,
                    (int) spentToday,
                    (int) spentThisWeek
            );
        }

        @Override
        public void onForeground(final SpendingStats newStats) {
            spendingStats.set(newStats);
        }
    }

}
