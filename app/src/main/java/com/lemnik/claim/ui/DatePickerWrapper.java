package com.lemnik.claim.ui;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jason on 2017/07/04.
 */

public class DatePickerWrapper implements View.OnFocusChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

    private final TextView display;

    private DatePickerDialog dialog = null;

    private Date currentDate = null;

    public DatePickerWrapper(final TextView display) {
        this.display = display;
        this.display.setFocusable(true);
        this.display.setClickable(true);
        this.display.setOnClickListener(this);
        this.display.setOnFocusChangeListener(this);

        this.setDate(new Date());
    }

    void openDatePickerDialog() {
        if (dialog == null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getDate());

            dialog = new DatePickerDialog(
                    display.getContext(),
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        dialog.show();
    }

    public void setDate(final Date date) {
        if (date == null) throw new IllegalArgumentException("date may not be null");
        this.currentDate = (Date) date.clone();
        this.display.setText(dateFormat.format(currentDate));

        if (this.dialog != null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getDate());
            this.dialog.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }
    }

    public Date getDate() {
        return currentDate;
    }

    @Override
    public void onClick(final View v) {
        openDatePickerDialog();
    }

    @Override
    public void onFocusChange(final View v, final boolean hasFocus) {
        if (hasFocus) {
            openDatePickerDialog();
        }
    }

    @Override
    public void onDateSet(
            final DatePicker view,
            final int year,
            final int month,
            final int dayOfMonth) {

        setDate(new GregorianCalendar(year, month, dayOfMonth).getTime());
    }
}
