/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.model.PostersProvider;
import org.nosemaj.restapp.poster.single.PosterContract.Interactor;
import org.nosemaj.restapp.poster.single.PosterContract.Model;
import org.nosemaj.restapp.poster.single.PosterContract.Presenter;
import org.nosemaj.restapp.poster.single.PosterContract;
import org.nosemaj.restapp.poster.single.PosterInteractor;
import org.nosemaj.restapp.poster.single.PosterModel;
import org.nosemaj.restapp.poster.single.PosterPresenter;
import org.nosemaj.restapp.poster.single.PosterView;
import org.nosemaj.restapp.util.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/**
 * Presenter of poster list.
 */
public final class PosterListAdapter extends RecyclerView.Adapter<PosterView> {

    private static final String TAG = PosterListAdapter.class.getName();

    private final int DEFAULT_POSTERS_COUNT = 8;

    private final Context context;
    private final PostersProvider postersProvider;
    private final Executor executor;
    private final List<PosterContract.Model> posterViewModels;

    /**
     * Constructs a new posters adapater.
     * @param postersProvider A posters provider
     */
    public PosterListAdapter(final Context context, final PostersProvider postersProvider, final Executor executor) {
        this.context = context;
        this.postersProvider = postersProvider;
        this.executor = executor;
        this.posterViewModels = new CopyOnWriteArrayList<>();
    }

    @Override
    public PosterView onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new PosterView(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(PosterView posterView, int position) {
        final PosterContract.Model model = posterViewModels.get(position);
        final PosterContract.Interactor interactor = new PosterInteractor(context, model);
        final PosterContract.Presenter presenter = new PosterPresenter(posterView, interactor);
        posterView.setOnPosterClickedListener(() -> presenter.posterClicked());
        presenter.displayPoster();
    }

    @Override
    public int getItemCount() {
        return posterViewModels.size();
    }

    /**
     * Displays posters, from the web.
     */
    public void updatePosters() {
        executor.execute(() -> {
            postersProvider.getPosters(
                DEFAULT_POSTERS_COUNT,
                posters -> {
                    posterViewModels.clear();
                    for (final Poster poster : posters) {
                        posterViewModels.add(PosterModel.create(poster.getMovieName(), poster.getPosterImageUrl()));
                    }
                    Log.d(TAG, "posters were added, size = " + posterViewModels.size());
                    invalidateDataset();
                }, error -> {
                    posterViewModels.clear();
                    invalidateDataset();
                    throw new RuntimeException("Uh oh.");
                });
        });
    }

    /**
     * Invalidate the dataset.
     */
    private void invalidateDataset() {
        new Handler(Looper.getMainLooper()).post(() -> notifyDataSetChanged());
    }
}

