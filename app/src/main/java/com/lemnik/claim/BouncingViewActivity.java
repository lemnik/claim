package com.lemnik.claim;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lemnik.claim.widget.BouncingDrawablesView;

import java.util.Random;

public class BouncingViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bouncing_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final BouncingDrawablesView bouncingDrawablesView = (BouncingDrawablesView) findViewById(R.id.bouncing_view);
        final BouncingDrawablesView.Bouncer[] bouncers = new BouncingDrawablesView.Bouncer[10];
        final Random random = new Random();
        final Resources res = getResources();

        final Drawable icon = res.getDrawable(R.drawable.ic_other_black);
        final int iconSize = res.getDimensionPixelSize(R.dimen.bouncing_icon_size);
        for (int i = 0; i < bouncers.length; i++) {
            final Rect bounds = new Rect();
            bounds.top = random.nextInt(400);
            bounds.left = random.nextInt(600);
            bounds.right = bounds.left + iconSize;
            bounds.bottom = bounds.top + iconSize;
            icon.setBounds(bounds);

            bouncers[i] = new BouncingDrawablesView.Bouncer(
                    icon,
                    random.nextBoolean() ? 6 : -6,
                    random.nextBoolean() ? 6 : -6
            );
        }

        bouncingDrawablesView.setBouncers(bouncers);
    }
}
