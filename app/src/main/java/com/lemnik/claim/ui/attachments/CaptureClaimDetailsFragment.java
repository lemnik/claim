package com.lemnik.claim.ui.attachments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lemnik.claim.R;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.widget.DatePickerLayout;

public class CaptureClaimDetailsFragment extends Fragment {

    private EditText description;

    private EditText amount;

    private DatePickerLayout selectedDate;

    private ClaimItem claimItem;

    public void setClaimItem(final ClaimItem claimItem) {
        this.claimItem = claimItem;

        // not in the book
        if (description != null && amount != null && selectedDate != null) {
            description.setText(claimItem.getDescription());
            amount.setText(Integer.toString((int) claimItem.getAmount()));
            selectedDate.setDate(claimItem.getTimestamp());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final @Nullable ViewGroup container,
            final @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_claim_capture_details, container, false);

        description = (EditText) root.findViewById(R.id.description);
        amount = (EditText) root.findViewById(R.id.amount);
        selectedDate = (DatePickerLayout) root.findViewById(R.id.date);

        // not in the book
        if (claimItem != null) {
            description.setText(claimItem.getDescription());
            amount.setText(Integer.toString((int) claimItem.getAmount()));
            selectedDate.setDate(claimItem.getTimestamp());
        }

        return root;
    }

    public void captureClaimItem() {
        claimItem.setDescription(description.getText().toString());

        if (!TextUtils.isEmpty(amount.getText())) {
            claimItem.setAmount(Double.parseDouble(amount.getText().toString()));
        }

        claimItem.setTimestamp(selectedDate.getDate());
    }
}
