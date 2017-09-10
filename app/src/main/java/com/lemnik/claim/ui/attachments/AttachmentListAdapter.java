package com.lemnik.claim.ui.attachments;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lemnik.claim.model.Attachment;
import com.lemnik.claim.widget.AttachmentPreview;

import java.util.Collections;
import java.util.List;

public class AttachmentListAdapter extends BaseAdapter {

    private List<Attachment> attachments = Collections.emptyList();

    public AttachmentListAdapter(
            final LifecycleOwner lifecycleOwner,
            final LiveData<List<Attachment>> attachments) {

        attachments.observe(lifecycleOwner, new Observer<List<Attachment>>() {
            @Override
            public void onChanged(final List<Attachment> attachments) {
                AttachmentListAdapter.this.attachments =
                        attachments != null
                                ? attachments
                                : Collections.emptyList();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return attachments.size();
    }

    @Override
    public Object getItem(final int i) {
        return attachments.get(i);
    }

    @Override
    public long getItemId(final int i) {
        return attachments.get(i).id;
    }

    @Override
    public View getView(
            final int i,
            final View view,
            final ViewGroup viewGroup) {

        AttachmentPreview preview = (AttachmentPreview) view;
        if (preview == null) {
            preview = new AttachmentPreview(viewGroup.getContext());
        }

        preview.setAttachment(attachments.get(i));

        return preview;
    }
}
