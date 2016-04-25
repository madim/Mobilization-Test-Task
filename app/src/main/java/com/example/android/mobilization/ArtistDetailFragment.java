package com.example.android.mobilization;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a single Artist detail screen.
 * This fragment is either contained in a {@link ArtistListActivity}
 * in two-pane mode (on tablets) or a {@link ArtistDetailActivity}
 * on handsets.
 */
public class ArtistDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_NAME = "item_name";
    public static final String ARG_ITEM_GENRES = "item_genres";
    public static final String ARG_ITEM_TRACKS = "item_tracks";
    public static final String ARG_ITEM_ALBUMs = "item_albums";
    public static final String ARG_ITEM_DESCRIPTION = "item_description";
    public static final String ARG_ITEM_COVER_BIG = "item_cover_big";

    boolean success = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArtistDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments().containsKey(ARG_ITEM_NAME)) {
            success = true;
            Activity activity = this.getActivity();
            ImageView backdropImage = (ImageView) activity.findViewById(R.id.backdrop);
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                Glide.with(getActivity()).load(getArguments().getString(ARG_ITEM_COVER_BIG)).into(backdropImage);
                appBarLayout.setTitle(getArguments().getString(ARG_ITEM_NAME));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (success) {
            String genreStr = "";

            for (String s : getArguments().getStringArrayList(ARG_ITEM_GENRES)) {
                genreStr += s + ", ";
            }
            genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                    genreStr.length() - 2) : genreStr;

            ((TextView) rootView.findViewById(R.id.genres)).setText(genreStr);
            ((TextView) rootView.findViewById(R.id.albums_and_tracks)).setText(getArguments().getString(ARG_ITEM_ALBUMs)
                        + " альбомов " + getArguments().getString(ARG_ITEM_TRACKS) + " треков");
            ((TextView) rootView.findViewById(R.id.description)).setText(getArguments().getString(ARG_ITEM_DESCRIPTION));

        }



        return rootView;
    }
}