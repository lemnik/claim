package com.lemnik.claim.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.lemnik.claim.R;
import com.lemnik.claim.util.ActionCommand;

public class SpendingGraphView extends View {

    private int positiveColor = Color.GREEN;
    private int negativeColor = Color.RED;
    private int targetColor = Color.GRAY;

    private double[] spendingPerDay;

    private Path spendingPath = null;
    private Paint spendingPaint = null;
    private float[] targetLine = null;
    private Paint targetPaint = null;

    public SpendingGraphView(final Context context) {
        super(context);
        init(null, 0);
    }

    public SpendingGraphView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SpendingGraphView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(final AttributeSet attrs, final int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SpendingGraphView, defStyle, 0);

        positiveColor = a.getColor(
                R.styleable.SpendingGraphView_positiveColor,
                positiveColor);

        negativeColor = a.getColor(
                R.styleable.SpendingGraphView_negativeColor,
                negativeColor);

        targetColor = a.getColor(
                R.styleable.SpendingGraphView_targetColor,
                targetColor);

        invalidateGraph();

        a.recycle();
    }

    protected void invalidateGraph() {

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (spendingPath == null || spendingPaint == null
                || targetLine == null || targetPaint == null) {

            return;
        }

        canvas.drawPath(spendingPath, spendingPaint);
        canvas.drawLines(targetLine, targetPaint);
    }

    public void setSpendingPerDay(final double[] spendingPerDay) {
        this.spendingPerDay = spendingPerDay;
        invalidateGraph();
    }

    public int getPositiveColor() {
        return positiveColor;
    }

    public void setPositiveColor(int positiveColor) {
        this.positiveColor = positiveColor;
        invalidateGraph();
    }

    public int getNegativeColor() {
        return negativeColor;
    }

    public void setNegativeColor(int negativeColor) {
        this.negativeColor = negativeColor;
        invalidateGraph();
    }

    public int getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(int targetColor) {
        this.targetColor = targetColor;
        invalidateGraph();
    }

    private class GenerateGraph extends ActionCommand<double[], Pair<Path, Paint>> {

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();

        final int contentWidth = getWidth() - paddingLeft - paddingRight;
        final int contentHeight = getHeight() - paddingTop - paddingBottom;

        Path createSpendingPath(final double[] amountPerDay) {
            final Path path = new Path();

            //TODO

            return path;
        }

        Paint createSpendingPaint() {
            final Paint paint = new Paint();

            //TODO

            return paint;
        }

        @Override
        public Pair<Path, Paint> onBackground(final double[] amountPerDay) throws Exception {
            return null;
        }

        @Override
        public void onForeground(final Pair<Path, Paint> graph) {
            spendingPath = graph.first;
            spendingPaint = graph.second;
            postInvalidate();
        }
    }

}
