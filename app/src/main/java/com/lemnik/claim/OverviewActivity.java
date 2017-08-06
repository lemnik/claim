package com.lemnik.claim;

import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lemnik.claim.model.Allowance;
import com.lemnik.claim.model.Category;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.ui.AllowanceOverviewFragment;

import java.util.Date;

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

    public void onUpdateSpending(final View v) {
        final ClaimItem item = new ClaimItem();
        item.setAmount(21);
        item.setDescription("Test");
        item.setTimestamp(new Date());
        item.setCategory(Category.OTHER);
        allowance.addClaimItem(item);
    }
}
