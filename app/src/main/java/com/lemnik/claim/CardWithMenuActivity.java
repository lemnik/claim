package com.lemnik.claim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewStub;

public class CardWithMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_with_menu);

        final CardView cardView = findViewById(R.id.card_with_menu);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final ViewStub menu = (ViewStub) findViewById(R.id.menu);
                menu.inflate();
                cardView.setOnClickListener(null);
            }
        });
    }
}
