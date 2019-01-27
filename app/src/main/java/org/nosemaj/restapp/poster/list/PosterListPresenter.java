/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.poster.single.PosterContract;
import org.nosemaj.restapp.poster.single.PosterInteractor;
import org.nosemaj.restapp.poster.single.PosterModel;
import org.nosemaj.restapp.poster.single.PosterPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter for poster list.
 */
public final class PosterListPresenter implements PosterListContract.Presenter {

    private final PosterListContract.View view;
    private final PosterListContract.Interactor interactor;
    private final Context context;
    private final List<PosterContract.Model> posterViewModels;

    private Disposable disposable;

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
        this.disposable = null;
    }

    @Override
    public void posterBecomingVisible(final PosterContract.View view, final int position) {
        // Create child VIPER module and call *its* presenter.
        PosterContract.Model model = posterViewModels.get(position);
        final PosterContract.Interactor interactor = new PosterInteractor(context, model);
        final PosterContract.Presenter presenter = new PosterPresenter(view, interactor);
        view.setOnPosterClickedListener(() -> presenter.posterClicked());
        presenter.displayPoster();
    }

    @Override
    public void viewCreated() {
        disposable = interactor.observePosters()
            .subscribeOn(Schedulers.io())
            .map(poster -> PosterModel.create(poster.getMovieName(), poster.getPosterImageUrl()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewModel -> {
                posterViewModels.add(viewModel);
                view.posterInsertedAt(0);
            }, error -> {
                view.clearAllPosters();
            });
    }

    @Override
    public void viewDestroyed() {
        disposable.dispose();
    }

    @Override
    public int getPosterCount() {
        return posterViewModels.size();
    }
}

