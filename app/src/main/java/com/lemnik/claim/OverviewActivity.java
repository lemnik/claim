package com.lemnik.claim;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lemnik.claim.ui.ClaimItemAdapter;

public class OverviewActivity
        extends AppCompatActivity
        implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        final RecyclerView claimItems = findViewById(R.id.claim_items);
        claimItems.setAdapter(new ClaimItemAdapter(
                this, this,
                ClaimApplication.getClaimDatabase().claimItemDao().selectAll()
        ));
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
