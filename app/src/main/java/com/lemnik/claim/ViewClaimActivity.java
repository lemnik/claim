package com.lemnik.claim;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;

import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.model.db.ClaimDatabase;
import com.lemnik.claim.ui.attachments.AttachmentListAdapter;
import com.lemnik.claim.ui.attachments.CaptureClaimDetailsFragment;

import static com.lemnik.claim.CaptureClaimActivity.EXTRA_CLAIM_ITEM;

/**
 * Not part of the book
 */
public class ViewClaimActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private final ClaimDatabase database = ClaimApplication.getClaimDatabase();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_claim);

        final ClaimItem claimItem = getIntent().getParcelableExtra(EXTRA_CLAIM_ITEM);
        setTitle(claimItem.getDescription());

        final CaptureClaimDetailsFragment captureClaimDetails =
                (CaptureClaimDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.capture_claim_details);
        captureClaimDetails.setClaimItem(claimItem);

        final AdapterView attachments = findViewById(R.id.attachments);
        attachments.setAdapter(new AttachmentListAdapter(
                this,
                database.attachmentDao().selectForClaimItemId(claimItem.id)
        ));
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
