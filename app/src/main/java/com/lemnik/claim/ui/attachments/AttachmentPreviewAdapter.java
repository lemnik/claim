package com.lemnik.claim.ui.attachments;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.lemnik.claim.model.Attachment;
import com.lemnik.claim.widget.AttachmentPreview;

import java.util.Collections;
import java.util.List;

public class AttachmentPreviewAdapter extends PagerAdapter {

    private List<Attachment> attachments = Collections.emptyList();

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments != null
                ? attachments
                : Collections.emptyList();

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return attachments.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final AttachmentPreview preview = new AttachmentPreview(container.getContext());
        preview.setAttachment(attachments.get(position));

        container.addView(preview);

        return attachments.get(position);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        for (int i = 0; i < container.getChildCount(); i++) {
            if (((AttachmentPreview) container.getChildAt(i)).getAttachment() == object) {
                container.removeViewAt(i);
                break;
            }
        }
    }

    @Override
    public boolean isViewFromObject(final View view, final Object o) {
        return (view instanceof AttachmentPreview) && (((AttachmentPreview) view).getAttachment() == o);
    }
}
