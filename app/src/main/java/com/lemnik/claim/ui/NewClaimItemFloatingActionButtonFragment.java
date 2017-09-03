package com.lemnik.claim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lemnik.claim.CaptureClaimActivity;
import com.lemnik.claim.ClaimApplication;
import com.lemnik.claim.R;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.model.db.ClaimDatabase;
import com.lemnik.claim.util.ActionCommand;

public class NewClaimItemFloatingActionButtonFragment
        extends Fragment
        implements View.OnClickListener {

    private static final int REQUEST_CODE_CREATE_CLAIM_ITEM = 100;

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        final View button = inflater.inflate(
                R.layout.fragment_new_claim_item_floating_action_button,
                container,
                false);
        button.setOnClickListener(this);
        return button;
    }

    @Override
    public void onClick(final View view) {
        startActivityForResult(
                new Intent(getContext(), CaptureClaimActivity.class),
                REQUEST_CODE_CREATE_CLAIM_ITEM);
    }

    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {

        if (requestCode != REQUEST_CODE_CREATE_CLAIM_ITEM
                || resultCode != Activity.RESULT_OK
                || data == null) {
            return;
        }

        final ClaimItem claimItem = data.getParcelableExtra(
                CaptureClaimActivity.EXTRA_CLAIM_ITEM
        );

        if (claimItem.isValid()) {
            final ClaimDatabase database = ClaimApplication.getClaimDatabase();
            AsyncTask.SERIAL_EXECUTOR.execute(
                    database.createClaimItemTask(claimItem)
            );
        }
    }
}
