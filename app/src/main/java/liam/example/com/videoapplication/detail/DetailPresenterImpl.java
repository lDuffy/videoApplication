package liam.example.com.videoapplication.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.model.Feed;
import liam.example.com.videoapplication.player.IExoPlayer;

public class DetailPresenterImpl implements DetailContract.DetailPresenter {
    private final Context context;
    private final IExoPlayer player;
    private DetailContract.DetailView view;
    private int resumeWindow;
    private int position;
    private long resumePosition;
    private Feed feed;

    @Inject
    public DetailPresenterImpl(Context context, IExoPlayer player) {
        this.context = context;
        this.player = player;
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
        resumePosition = player.getResumePosition();
    }

    @Override
    public void setArguments(Bundle bundle) {
        if (null != bundle) {
            feed = (Feed) bundle.getSerializable(ARG_FEED);
            position = bundle.getInt(ARG_POSITION);

        }

    }

    @Override
    public void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    private synchronized void initializePlayer() {
        if (player.isPlayerNull()) {

            player.init(context, this);
            player.setMediaSource(getList());

            view.setPlayerView(player.getSimpleExoPlayer());

            boolean haveResumePosition = C.INDEX_UNSET != resumeWindow;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare();
        }

    }

    @NonNull
    public List<Uri> getList() {
        return feed.getListOrderedByPosition(position);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        //unused
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        incrementPosition();
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
        view.showToast(context.getString(R.string.video_playback_error));
        incrementPosition();
        clearResumePosition();
        player.clearExoPLayer();
        initializePlayer();

    }

    private void incrementPosition() {
        position = (position + 2) % feed.getListLength();
    }

    @Override
    public void onPositionDiscontinuity() {
        //unused

    }
}
