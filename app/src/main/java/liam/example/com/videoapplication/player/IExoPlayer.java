package liam.example.com.videoapplication.player;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * wrapper interface for exoplayer
 */

public interface IExoPlayer {

    void release();

    void setPlaybackSpeed(float speed);

    void seekTo(int windowIndex, long positionMs);

    void prepare();

    void newInstance(Context context);

    void init(Context context, com.google.android.exoplayer2.ExoPlayer.EventListener listener);

    void setMediaSource(List<Uri> listOrderedByPosition);

    void clearExoPLayer();

    boolean isPlayerNull();

    int getCurrentWindowIndex();

    long getResumePosition();

    SimpleExoPlayer getSimpleExoPlayer();
}
