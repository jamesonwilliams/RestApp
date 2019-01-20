/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.OnPostersAvailableListener;
import org.nosemaj.restapp.model.OnPostersNotAvailableListener;
import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.poster.single.PosterContract;
import org.nosemaj.restapp.poster.single.PosterInteractor;
import org.nosemaj.restapp.poster.single.PosterModel;
import org.nosemaj.restapp.poster.single.PosterPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for poster list.
 */
public final class PosterListPresenter implements PosterListContract.Presenter,
        OnPostersAvailableListener, OnPostersNotAvailableListener {

    private final PosterListContract.View view;
    private final PosterListContract.Interactor interactor;
    private final Context context;
    private final List<PosterContract.Model> posterViewModels;

    /**
     * Constructs a poster list presenter.
     * @param view Poster list view
     * @param interactor Poster list interactor
     * @param context An Android context
     */
    public PosterListPresenter(
            final PosterListContract.View view,
            final PosterListContract.Interactor interactor,
            final Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
        this.posterViewModels = new ArrayList<>();
    }

    @Override
    public void showPosterAtPosition(final PosterContract.View view, final int position) {
        // Create child VIPER module and call *its* presenter.
        PosterContract.Model model;
        synchronized (posterViewModels) {
            model = posterViewModels.get(position);
        }
        final PosterContract.Interactor interactor = new PosterInteractor(context, model);
        final PosterContract.Presenter presenter = new PosterPresenter(view, interactor);
        view.setOnPosterClickedListener(() -> presenter.posterClicked());
        presenter.displayPoster();
    }

    /**
     * Displays the poster list.
     */
    @Override
    public void refreshPosters() {
        // Callbacks occur on a different thread
        interactor.getPosters(this::onPostersAvailable, this::onPostersNotAvailable);
    }

    @Override
    public int getListLength() {
        synchronized (posterViewModels) {
            return posterViewModels.size();
        }
    }

    @Override
    public void onPostersAvailable(final List<Poster> posters) {
        synchronized (posterViewModels) {
            posterViewModels.clear();
            for (final Poster poster : posters) {
                posterViewModels.add(PosterModel.create(poster.getMovieName(), poster.getPosterImageUrl()));
            }
        }
        view.invalidateView();
    }

    @Override
    public void onPostersNotAvailable(final Throwable error) {
        synchronized (posterViewModels) {
            posterViewModels.clear();
        }
        view.invalidateView();
    }
}

