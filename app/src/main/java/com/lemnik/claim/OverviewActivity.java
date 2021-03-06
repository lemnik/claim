package com.lemnik.claim;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.model.db.ClaimDatabase;
import com.lemnik.claim.ui.ClaimItemAdapter;
import com.lemnik.claim.ui.DataBoundViewHolder;
import com.lemnik.claim.util.ActionCommand;

public class OverviewActivity
        extends AppCompatActivity
        implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setSupportActionBar(findViewById(R.id.toolbar));

        final RecyclerView claimItems = findViewById(R.id.claim_items);
        claimItems.setAdapter(new ClaimItemAdapter(
                this, this,
                ClaimApplication.getClaimDatabase().claimItemDao().selectAll()
        ));

        new ItemTouchHelper(new SwipeToDeleteCallback())
                .attachToRecyclerView(claimItems);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    class DeleteClaimItemCommand
            extends ActionCommand<Void, Void>
            implements View.OnClickListener {

        private final ClaimDatabase database = ClaimApplication.getClaimDatabase();

        private final ClaimItem item;

        public DeleteClaimItemCommand(final ClaimItem item) {
            this.item = item;
        }

        @Override
        public Void onBackground(final Void noArgs) {
            database.claimItemDao().delete(item);
            return null;
        }

        @Override
        public void onForeground(final Void noArgs) {
            final String message = getString(
                    R.string.msg_claim_item_deleted,
                    item.getDescription());
            final View scaffolding = findViewById(R.id.scaffolding);
            Snackbar.make(scaffolding, message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, this)
                    .show();
        }

        public void onClick(final View view) {
            AsyncTask.SERIAL_EXECUTOR.execute(database.createClaimItemTask(item));
        }
    }

    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        final float defaultElevation = getResources().getDimensionPixelSize(R.dimen.cardview_default_elevation);

        SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(
                final RecyclerView recyclerView,
                final RecyclerView.ViewHolder viewHolder,
                final RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onChildDraw(
                final Canvas c,
                final RecyclerView recyclerView,
                final RecyclerView.ViewHolder viewHolder,
                final float dX, final float dY,
                final int actionState,
                final boolean isCurrentlyActive) {

            if (isCurrentlyActive) {
                ViewCompat.setElevation(
                        viewHolder.itemView,
                        Math.min(
                                Math.max(dX / 4f, defaultElevation),
                                defaultElevation * 16f
                        )
                );
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(
                final RecyclerView recyclerView,
                final RecyclerView.ViewHolder viewHolder) {
            ViewCompat.setElevation(viewHolder.itemView, defaultElevation);
            super.clearView(recyclerView, viewHolder);
        }

        @Override
        public void onSwiped(
                final RecyclerView.ViewHolder viewHolder,
                final int direction) {

            final DataBoundViewHolder<?, ClaimItem> holder = (DataBoundViewHolder<?, ClaimItem>) viewHolder;
            new DeleteClaimItemCommand(holder.getItem()).exec(null);
        }

        @Override
        public int getMovementFlags(
                final RecyclerView recyclerView,
                final RecyclerView.ViewHolder viewHolder) {

            if (viewHolder.getAdapterPosition() == 0) {
                return 0;
            }

            return super.getMovementFlags(recyclerView, viewHolder);
        }
    }
}
