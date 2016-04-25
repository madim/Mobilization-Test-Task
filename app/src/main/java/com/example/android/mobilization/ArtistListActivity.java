package com.example.android.mobilization;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Artists. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArtistDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArtistListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private List<Artist> mArtists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        updateItems();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.artist_list);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setupAdapter();

        if (findViewById(R.id.artist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void updateItems() {
        if(mRecyclerView != null) {
            mArtists.clear();
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
        new FetchItemTask().execute();
    }

    private void setupAdapter() {
        mRecyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mArtists));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Artist> mArtists;

        public SimpleItemRecyclerViewAdapter(List<Artist> artists) {
            mArtists = artists;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.artist_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Artist artist = mArtists.get(position);
            holder.bindArtist(artist);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        arguments.putString(ArtistDetailFragment.ARG_ITEM_NAME, holder.mArtist.getName());
                        arguments.putStringArrayList(ArtistDetailFragment.ARG_ITEM_GENRES, holder.mArtist.getGenres());
                        arguments.putInt(ArtistDetailFragment.ARG_ITEM_TRACKS, holder.mArtist.getTracks());
                        arguments.putInt(ArtistDetailFragment.ARG_ITEM_ALBUMs, holder.mArtist.getAlbums());
                        arguments.putString(ArtistDetailFragment.ARG_ITEM_DESCRIPTION, holder.mArtist.getDescription());
                        arguments.putString(ArtistDetailFragment.ARG_ITEM_COVER_BIG, holder.mArtist.getCover().getBig());

                        ArtistDetailFragment fragment = new ArtistDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.artist_detail_container, fragment)
                                .commit();
                    } else {

                        Context context = v.getContext();

                        Intent intent = new Intent(context, ArtistDetailActivity.class);

                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_NAME, holder.mArtist.getName());
                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_GENRES, holder.mArtist.getGenres());
                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_TRACKS, holder.mArtist.getTracks()+"");
                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_ALBUMs, holder.mArtist.getAlbums()+"");
                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_DESCRIPTION, holder.mArtist.getDescription());
                        intent.putExtra(ArtistDetailFragment.ARG_ITEM_COVER_BIG, holder.mArtist.getCover().getBig());

                        Log.d("ArtistListActivity", "onClick: " + holder.mArtist.getId());

                        context.startActivity(intent);
                    }
                }

            });
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageCover;
            public final TextView mNameText;
            public final TextView mGenresText;
            public final TextView mAlbumsAndTracksText;

            public Artist mArtist;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageCover = (ImageView) view.findViewById(R.id.thumbnail);
                mNameText = (TextView) view.findViewById(R.id.name);
                mGenresText = (TextView) view.findViewById(R.id.genres);
                mAlbumsAndTracksText = (TextView) view.findViewById(R.id.albums_and_tracks);
            }

            public void bindArtist(Artist artist) {
                mArtist = artist;
                Log.d("ArtistListActivity", "bindArtist: " + mArtist.getName());
                Glide.with(getApplicationContext()).load(mArtist.getCover().getSmall()).into(mImageCover);
                mNameText.setText(mArtist.getName());

                String genreStr = "";

                for (String s : mArtist.getGenres()) {
                    genreStr += s + ", ";
                }
                genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                        genreStr.length() - 2) : genreStr;

                mGenresText.setText(genreStr);

                mAlbumsAndTracksText.setText(mArtist.getAlbums() + " альбомов, " + mArtist.getTracks() + " песен");
            }

        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(Void... params) {
            return new Fetcher().fetchData();
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);
            mArtists = artists;

            setupAdapter();
        }
    }
}