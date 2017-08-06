package com.lemnik.claim.ui.attachments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lemnik.claim.R;
import com.lemnik.claim.model.Attachment;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.model.commands.CreateAttachmentCommand;
import com.lemnik.claim.util.ActionCommand;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class AttachmentPagerFragment extends Fragment {

    private static final int REQUEST_ATTACH_FILE = 1;
    private static final int REQUEST_ATTACH_PERMISSION = 1001;

    private final AttachmentPreviewAdapter adapter = new AttachmentPreviewAdapter();

    private ActionCommand<Uri, Attachment> attachFileCommand;
    private ViewPager pager;

    private ClaimItem claimItem;

    public void setClaimItem(final ClaimItem claimItem) {
        this.claimItem = claimItem;
        onAttachmentsChanged();
    }

    public void onAttachmentsChanged() {
        adapter.setAttachments(claimItem != null ? claimItem.getAttachments() : null);
        pager.setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final File attachmentsDir = getContext().getDir("attachments", MODE_PRIVATE);
        attachFileCommand = new CreateAttachmentCommand(attachmentsDir, getContext().getContentResolver()) {
            @Override
            public void onForeground(final Attachment value) {
                if (claimItem != null) {
                    claimItem.addAttachment(value);
                    onAttachmentsChanged();
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        claimItem = null;
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final @Nullable ViewGroup container,
            final @Nullable Bundle savedInstanceState) {

        pager = (ViewPager) inflater.inflate(R.layout.fragment_attachment_pager, container, false);
        pager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.grid_spacer1));
        pager.setAdapter(adapter);

        return pager;
    }

    public void onAttachClick() {
        final int permissionStatus = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_ATTACH_PERMISSION);

            return;
        }

        final Intent attach = new Intent(Intent.ACTION_GET_CONTENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("*/*");

        startActivityForResult(attach, REQUEST_ATTACH_FILE);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {

        switch (requestCode) {
            case REQUEST_ATTACH_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onAttachClick();
                }
                break;
        }
    }

    public void onAttachFileResult(final int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null || data.getData() == null) {
            return;
        }

        attachFileCommand.exec(data.getData());
    }

    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {

        switch (requestCode) {
            case REQUEST_ATTACH_FILE:
                onAttachFileResult(resultCode, data);
                break;
        }
    }

}
