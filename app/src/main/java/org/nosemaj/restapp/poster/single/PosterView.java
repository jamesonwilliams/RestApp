/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.nosemaj.restapp.R;

/**
 * A view holder for an individual poster.
 */
public final class PosterView extends RecyclerView.ViewHolder implements PosterContract.View {

    private final String TAG = PosterView.class.getName();

    private TextView movieTitleView;
    private ImageView posterArtView;
    private OnPosterClickedListener onPosterClickedListener;

    /**
     * Constructs a new PosterView.
     * @param inflater A way to infalte the poster view
     * @param container The parent of this view
     */
    public PosterView(final LayoutInflater inflater, final ViewGroup container) {
        super(inflater.inflate(R.layout.single_poster_view, container, false));
        this.movieTitleView = itemView.findViewById(R.id.movie_title_view);
        this.posterArtView = itemView.findViewById(R.id.poster_art_view);
        this.onPosterClickedListener = null;
        itemView.setOnClickListener(ignoredParameter -> {
            if (onPosterClickedListener != null) {
                onPosterClickedListener.onPosterClicked();
            }
        });
    }

    @Override
    public void setMovieTitle(final String movieTitle) {
        movieTitleView.setText(movieTitle);
    }

    @Override
    public void setPosterBackground(final String imageUrl) {
        Log.i(TAG, "setPosterBackground(" + imageUrl + ")");
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(posterArtView);
    }

    @Override
    public void setOnPosterClickedListener(final OnPosterClickedListener onPosterClickedListener) {
        this.onPosterClickedListener = onPosterClickedListener;
    }
}

