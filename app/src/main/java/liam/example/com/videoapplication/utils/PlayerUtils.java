package liam.example.com.videoapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import java.util.List;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import liam.example.com.videoapplication.VideoApplication;

public final class PlayerUtils {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private PlayerUtils() {
    }

    public static SimpleExoPlayer getPlayer(Context context) {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, new DefaultLoadControl(),
                null, SimpleExoPlayer.EXTENSION_RENDERER_MODE_OFF);
        player.setPlayWhenReady(true);

        return player;
    }

    public static MediaSource buildMediaSource(Uri source, Handler handler) {
        return new ExtractorMediaSource(source, buildDataSourceFactory(), new DefaultExtractorsFactory(),
                handler, null);
    }

    private static DataSource.Factory buildDataSourceFactory() {
        return VideoApplication.getInstance().buildDataSourceFactory(BANDWIDTH_METER);
    }

    public static MediaSource getMediaSource(List<Uri> uris) {
        Handler handler = new Handler();
        MediaSource[] mediaSources = new MediaSource[uris.size()];
        for (int i = 0; i < uris.size(); i++) {
            mediaSources[i] = buildMediaSource(uris.get(i), handler);
        }
        return (1 == mediaSources.length) ? mediaSources[0]
                : new ConcatenatingMediaSource(mediaSources);
    }
}
