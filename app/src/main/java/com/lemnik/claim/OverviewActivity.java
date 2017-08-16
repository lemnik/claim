package com.lemnik.claim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lemnik.claim.model.Allowance;
import com.lemnik.claim.ui.AllowanceOverviewFragment;

public class OverviewActivity extends AppCompatActivity {

    private Allowance allowance;

    private AllowanceOverviewFragment allowanceOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        allowanceOverview = (AllowanceOverviewFragment) getSupportFragmentManager().findFragmentById(R.id.allowance_overview);

        allowance = new Allowance(150);
        allowanceOverview.setAllowance(allowance);
    }

}
