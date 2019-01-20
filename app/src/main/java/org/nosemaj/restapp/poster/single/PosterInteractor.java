/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.nosemaj.restapp.util.Preconditions;

/**
 * PosterInteractor handles a poster UI's interactions with the outside
 * world (data providers, other modules, etc).
 */
public final class PosterInteractor implements PosterContract.Interactor {

    private final Context context;
    private final PosterContract.Model model;

    /**
     * Constructs a PosterInteractor.
     * @param model A Poster model
     */
    public PosterInteractor(@NonNull final Context context, @NonNull final PosterContract.Model model) {
        Preconditions.notNull(context, "Refusing to construct interactor with null context.");
        Preconditions.notNull(model, "Refusing to construct interactor for null poster model.");
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public PosterContract.Model getPoster() {
        return this.model;
    }

    @Override
    public void goToMovieDetails(@NonNull final PosterContract.Model model) {
        Preconditions.notNull(model, "Can't get movie details for null model!");
        final String uri = "https://www.google.com/search?q=" + model.getMovieName();
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

