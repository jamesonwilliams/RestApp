/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.nosemaj.restapp.PosterApplication;
import org.nosemaj.restapp.R;

import javax.inject.Inject;

/**
 * A simple activity which will display a list of video posters. The user may *
 * click on a poster to begin playback.
 */
public final class PosterListActivity extends AppCompatActivity {

    @Inject PosterListAdapter posterListAdapter;
    @Inject LinearLayoutManager linearLayoutManager;
    private RecyclerView posterListView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PosterApplication) getApplication())
            .getApplicationComponent()
            .inject(this);

        // Bind posters list view to adapter
        posterListView = (RecyclerView) findViewById(R.id.poster_list_view);
        posterListView.setLayoutManager(linearLayoutManager);
        posterListView.setAdapter(posterListAdapter);

        // Update the posters, if needed
        posterListAdapter.updatePosters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        posterListView.setLayoutManager(null);
        posterListView.setAdapter(null);
    }
}

