/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.os.Bundle;
import android.os.Parcelable;
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
    PosterListContract.Presenter presenter;
    private RecyclerView posterListView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((PosterApplication) getApplication())
            .getApplicationComponent()
            .inject(this);

        presenter = new PosterListPresenter(this, interactor, this);
        presenter.viewCreated();

        // Setup the view
        setContentView(R.layout.activity_main);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Parcelable layoutState = null;
        if (savedInstanceState != null) layoutState = savedInstanceState.getParcelable("LLM");
        if (layoutState != null) layoutManager.onRestoreInstanceState(layoutState);

        posterListView = (RecyclerView) findViewById(R.id.poster_list_view);
        posterListView.setLayoutManager(layoutManager);
        posterListView.setAdapter(new PosterListAdapter());
    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        savedInstanceState.putParcelable("LLM", posterListView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        presenter.viewDestroyed();
        presenter = null;
        posterListView.setLayoutManager(null);
        posterListView.setAdapter(null);
        posterListView = null;
        super.onDestroy();
    }

    @Override
    public void posterInsertedAt(int position) {
        posterListView.getAdapter().notifyItemInserted(position);
    }

    @Override
    public void clearAllPosters() {
        posterListView.getAdapter().notifyDataSetChanged();
    }

    /**
     * PosterListAdapter is a view adapter; part of the implementation
     * of a PosterListContract.View. Broken out as a subclass for
     * maintainability.
     */
    final class PosterListAdapter extends RecyclerView.Adapter<PosterView> {
        @Override
        public PosterView onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new PosterView(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(PosterView posterView, int position) {
            presenter.posterBecomingVisible(posterView, position);
        }

        @Override
        public int getItemCount() {
            return presenter.getPosterCount();
        }
    }
}

