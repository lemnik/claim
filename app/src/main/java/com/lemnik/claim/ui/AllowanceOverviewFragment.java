package com.lemnik.claim.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lemnik.claim.R;
import com.lemnik.claim.databinding.FragmentAllowanceOverviewBinding;
import com.lemnik.claim.model.Allowance;
import com.lemnik.claim.ui.presenters.AllowanceOverviewPresenter;

public class AllowanceOverviewFragment extends Fragment {

    private Allowance allowance;
    private FragmentAllowanceOverviewBinding binding;

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        this.binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_allowance_overview,
                container,
                false);

        if (this.allowance != null) {
            this.binding.setPresenter(new AllowanceOverviewPresenter(allowance));
        }

        return this.binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.binding != null && this.binding.getPresenter() != null) {
            this.binding.getPresenter().detach();
        }
    }

    public void setAllowance(final Allowance allowance) {
        this.allowance = allowance;

        if (this.binding != null) {
            if (this.binding.getPresenter() != null) {
                this.binding.getPresenter().detach();
            }

            this.binding.setPresenter(new AllowanceOverviewPresenter(allowance));
        }
    }

}
