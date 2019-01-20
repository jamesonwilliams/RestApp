/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.nosemaj.restapp.R;
import org.nosemaj.restapp.application.PosterApplication;
import org.nosemaj.restapp.poster.single.PosterView;

import javax.inject.Inject;

/**
 * A simple activity which will display a list of video posters. The user may *
 * click on a poster to begin playback.
 */
public final class PosterListActivity extends AppCompatActivity
        implements PosterListContract.View {

    @Inject PosterListContract.Interactor interactor;
    private RecyclerView posterListView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PosterApplication) getApplication())
            .getApplicationComponent()
            .inject(this);

        final PosterListContract.Presenter presenter =
            new PosterListPresenter(this, interactor, this);

        // Setup the view
        posterListView = (RecyclerView) findViewById(R.id.poster_list_view);
        posterListView.setLayoutManager(new LinearLayoutManager(this));
        posterListView.setAdapter(new PosterListAdapter(presenter));

        // Call presenter to update the posters
        presenter.refreshPosters();
    }

    @Override
    public void invalidateView() {
        runOnUiThread(() -> posterListView.getAdapter().notifyDataSetChanged());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        posterListView.setLayoutManager(null);
        posterListView.setAdapter(null);
    }

    /**
     * PosterListAdapter is a view adapter; part of the implementation
     * of a PosterListContract.View. Broken out as a subclass for
     * maintainability.
     */
    static final class PosterListAdapter extends RecyclerView.Adapter<PosterView> {
        private final PosterListContract.Presenter presenter;

        /**
         * Constructs a PosterListAdapter.
         * @param presenter A poster list presenter
         */
        PosterListAdapter(final PosterListContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public PosterView onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new PosterView(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(PosterView posterView, int position) {
            presenter.showPosterAtPosition(posterView, position);
        }

        @Override
        public int getItemCount() {
            return presenter.getListLength();
        }
    }
}

