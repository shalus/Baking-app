package com.shalu.letsbake;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.shalu.letsbake.DetailActivity.recipeIndex;
import static com.shalu.letsbake.RecipeStep.stepIndex;
import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Created by sarum on 11/19/2017.
 */

public class RecipeStepFragment extends Fragment {

    SimpleExoPlayer player;
    @BindView(R.id.player_view) SimpleExoPlayerView mPlayerView;
    @Nullable @BindView(R.id.button_prev) Button button_prev;
    @Nullable @BindView(R.id.button_next) Button button_next;
    @Nullable @BindView(R.id.tv_desc) TextView description;
    String userAgent;
    Unbinder unbinder;
    int orientation;
    private static final String SELECTED_POSITION = "position";
    private static long position;
    Uri videoUri;
    public RecipeStepFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step,container,false);
        unbinder = ButterKnife.bind(this, view);
        orientation = getResources().getConfiguration().orientation;
        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
        }
        refreshViews();
        if(orientation != ORIENTATION_LANDSCAPE) {
            button_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stepIndex < results[recipeIndex].steps.length)
                        stepIndex++;
                    refreshViews();
                }
            });
            button_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stepIndex > 1)
                        stepIndex--;
                    refreshViews();
                }
            });
        }


        return view;
    }

    public void preparePlayer(Uri videoUri) {
        if (player == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            // Prepare the MediaSource.
            userAgent = Util.getUserAgent(getContext(), "LetsBake");
        }

        mPlayerView.setPlayer(player);
        MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        player.prepare(mediaSource);
        if (position != C.TIME_UNSET) player.seekTo(position);
        player.setPlayWhenReady(true);
    }


    public void refreshViews() {
        if(orientation!=ORIENTATION_LANDSCAPE) {
            if (stepIndex == 0)
                button_prev.setEnabled(false);
            else
                button_prev.setEnabled(true);
            if (stepIndex == results[recipeIndex].steps.length - 1)
                button_next.setEnabled(false);
            else
                button_next.setEnabled(true);
            description.setText(results[recipeIndex].steps[stepIndex].description);
        }
        videoUri = Uri.parse(results[recipeIndex].steps[stepIndex].videoURL);
        if(videoUri==null || videoUri.equals(Uri.EMPTY)){
            mPlayerView.setVisibility(View.INVISIBLE);
        }
        else
            mPlayerView.setVisibility(View.VISIBLE);

        preparePlayer(videoUri);
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null)
                preparePlayer(videoUri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            position = player.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
