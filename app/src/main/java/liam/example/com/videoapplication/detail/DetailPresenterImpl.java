package liam.example.com.videoapplication.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.model.Feed;
import liam.example.com.videoapplication.utils.PlayerUtils;

public class DetailPresenterImpl implements DetailContract.DetailPresenter {
    private final Context context;
    private DetailContract.DetailView view;
    private SimpleExoPlayer player;
    private int resumeWindow;
    private int position;
    private long resumePosition;
    private Feed feed;

    @Inject
    public DetailPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onViewAttached(DetailContract.DetailView view) {
        this.view = view;
        initializePlayer();
    }

    @Override
    public void onViewDetached() {
        releasePlayer();
        view = null;
    }

    private void releasePlayer() {
        if (null != player) {
            player.release();
            updateResumePosition();
        }
    }

    @Override
    public void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition()) : C.TIME_UNSET;
    }

    @Override
    public void setArguments(Bundle bundle) {
        if (bundle != null) {
            feed = (Feed) bundle.getSerializable(ARG_FEED);
            position = bundle.getInt(ARG_POSITION);

        }

    }

    @Override
    public void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    private void initializePlayer() {
        if (null == player) {

            player = PlayerUtils.getPlayer(context);
            player.setPlaybackSpeed(1.0f);
            player.addListener(this);
            view.setPlayerView(player);
            player.setPlayWhenReady(true);

            List<Uri> uris = feed.getList(position);
            MediaSource mediaSource = PlayerUtils.getMediaSource(uris);
            boolean haveResumePosition = C.INDEX_UNSET != resumeWindow;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(mediaSource, false, false);
        }

    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        //unused
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        //unused
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        //unused
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (ExoPlayer.STATE_BUFFERING == playbackState) {
            view.setProgressVisible(View.VISIBLE);
        } else if (ExoPlayer.STATE_READY == playbackState) {
            view.setProgressVisible(View.GONE);
        } else if (ExoPlayer.DOUBLE_SPEED == playbackState) {
            view.showToast(context.getString(R.string.doubleSpeed));
        } else if (ExoPlayer.NORMAL_SPEED == playbackState) {
            view.showToast(context.getString(R.string.normalspeed));
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        view.showToast("Error Playing Video");
        position += 1;
        clearResumePosition();
        player = null;
        initializePlayer();

    }

    @Override
    public void onPositionDiscontinuity() {
        //unused

    }
}
