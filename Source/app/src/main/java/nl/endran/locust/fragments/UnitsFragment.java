/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.endran.locust.R;
import nl.endran.locust.game.Unit;
import nl.endran.locust.injections.AppComponent;
import nl.endran.locust.injections.AppGraph;

public class UnitsFragment extends Fragment {

    @Bind(R.id.textViewFoodCount)
    TextView textViewFoodCount;

    @Bind(R.id.textViewNymphCount)
    TextView textViewNymphCount;

    private UnitsFragmentPresenter presenter;

    public static UnitsFragment createInstance() {
        return new UnitsFragment();
    }

    @NonNull
    protected AppComponent getAppComponent() {
        return ((AppGraph) getContext().getApplicationContext()).getAppComponent();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_units, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        AppComponent appComponent = getAppComponent();
        presenter = appComponent.createUnitsFragmentPresenter();
        presenter.start(new UnitsFragmentPresenter.ViewModel() {
            @Override
            public void updateUnitCount(final Map<Unit, Double> unitCountMap) {
                double foodCount = unitCountMap.get(Unit.FOOD);
                textViewFoodCount.setText(String.format("COUNT = %d", ((int) foodCount)));

                double nymphCount = unitCountMap.get(Unit.NYMPH);
                textViewNymphCount.setText(String.format("COUNT = %d", ((int) nymphCount)));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.stop();
        presenter = null;
    }
}
