package liam.example.com.videoapplication.detail;

import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class DetailContract {

    interface DetailView {
        void showToast(String error);
        void setProgressVisible(int visibility);
        void setPlayerView(SimpleExoPlayer player);
    }

    public interface DetailPresenter extends ExoPlayer.EventListener {
        String ARG_FEED = "feed";
        String ARG_POSITION = "position";
        void onViewAttached(DetailView view);
        void onViewDetached();
        void clearResumePosition();
        void updateResumePosition();
        void setArguments(Bundle bundle);
    }
}
