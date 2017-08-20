package com.lemnik.claim;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lemnik.claim.model.Category;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.ui.CategoryPickerFragment;
import com.lemnik.claim.ui.attachments.AttachmentPagerFragment;
import com.lemnik.claim.ui.attachments.CaptureClaimDetailsFragment;

import java.util.Date;

public class CaptureClaimActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_CLAIM_ITEM = "com.packtpub.claim.extras.CLAIM_ITEM";

    private static final String KEY_CLAIM_ITEM = "com.packtpub.claim.ClaimItem";

    private CaptureClaimDetailsFragment details;
    private CategoryPickerFragment categories;
    private AttachmentPagerFragment attachments;

    private ClaimItem claimItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_claim);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton attach = findViewById(R.id.attach);
        attach.setOnClickListener(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        details = (CaptureClaimDetailsFragment) fragmentManager.findFragmentById(R.id.details);
        attachments = (AttachmentPagerFragment) fragmentManager.findFragmentById(R.id.attachments);
        categories = (CategoryPickerFragment) fragmentManager.findFragmentById(R.id.categories);

        if (savedInstanceState != null) {
            claimItem = savedInstanceState.getParcelable(KEY_CLAIM_ITEM);
        } else if (getIntent().hasExtra(EXTRA_CLAIM_ITEM)) {
            claimItem = getIntent().getParcelableExtra(EXTRA_CLAIM_ITEM);
        }

        if (claimItem == null) {
            claimItem = new ClaimItem();
            claimItem.setDescription("");
            claimItem.setAmount(0);
            claimItem.setTimestamp(new Date());
            claimItem.setCategory(Category.OTHER);
        }

        details.setClaimItem(claimItem);
        attachments.setClaimItem(claimItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        details.captureClaimItem();
        claimItem.setCategory(categories.getSelectedCategory());

        outState.putParcelable(KEY_CLAIM_ITEM, claimItem);
    }

    @Override
    public void finish() {
        details.captureClaimItem();
        claimItem.setCategory(categories.getSelectedCategory());

        setResult(RESULT_OK, new Intent().putExtra(EXTRA_CLAIM_ITEM, claimItem));
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attach:
                attachments.onAttachClick();
                break;
        }
    }

}
