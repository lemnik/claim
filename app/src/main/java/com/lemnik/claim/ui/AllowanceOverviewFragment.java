package com.lemnik.claim.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lemnik.claim.R;
import com.lemnik.claim.databinding.FragmentAllowanceOverviewBinding;
import com.lemnik.claim.ui.presenters.AllowanceOverviewPresenter;

public class AllowanceOverviewFragment extends LifecycleFragment {

    private FragmentAllowanceOverviewBinding binding;
    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferences = getContext().getSharedPreferences(
                "Allowance",
                Context.MODE_PRIVATE
        );
    }

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        this.binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_allowance_overview,
                container,
                false
        );

        this.binding.setPresenter(new AllowanceOverviewPresenter(
                this,
                preferences.getInt("allowancePerDay", 150)
        ));

        return this.binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        preferences.edit()
                .putInt("allowancePerDay", this.binding.getPresenter().allowance.get())
                .apply();
    }
}
