package liam.example.com.videoapplication.player;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import liam.example.com.videoapplication.utils.PlayerUtils;

/**
 * implementation of IExoPlayer
 */

public class ExoPlayerImpl implements IExoPlayer {

    SimpleExoPlayer player;
    MediaSource source;

    @Override
    public void release() {
        player.release();

    }

    @Override
    public int getCurrentWindowIndex() {
        return player.getCurrentWindowIndex();
    }

    @Override
    public long getResumePosition() {
        return player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition()) : C.TIME_UNSET;
    }

    @Override
    public void setPlaybackSpeed(float speed) {
        player.setPlaybackSpeed(speed);
    }

    @Override
    public void seekTo(int windowIndex, long positionMs) {
        player.seekTo(windowIndex, positionMs);
    }

    @Override
    public void prepare() {
        player.prepare(source, false, false);
    }

    @Override
    public void newInstance(Context context) {
        player = PlayerUtils.getPlayer(context);
    }

    @Override
    public SimpleExoPlayer getSimpleExoPlayer() {
        return player;
    }

    @Override
    public boolean isPlayerNull() {
        return null == player;
    }

    @Override
    public void init(Context context, ExoPlayer.EventListener listener) {
        newInstance(context);
        setPlaybackSpeed(1.0f);
        player.addListener(listener);
        player.setPlayWhenReady(true);
    }

    @Override
    public void setMediaSource(List<Uri> list) {
        source = PlayerUtils.getMediaSource(list);
    }

    @Override
    public void clearExoPLayer() {
        player = null;
    }


}
