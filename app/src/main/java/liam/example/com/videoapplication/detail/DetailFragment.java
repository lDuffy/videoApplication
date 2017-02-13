package liam.example.com.videoapplication.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;

import javax.inject.Inject;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.dagger.components.ActivityComponent;
import liam.example.com.videoapplication.main.MainActivity;

import static liam.example.com.videoapplication.detail.DetailContract.DetailPresenter.ARG_FEED;
import static liam.example.com.videoapplication.detail.DetailContract.DetailPresenter.ARG_POSITION;


public class DetailFragment extends Fragment implements DetailContract.DetailView {

    public static final int VERSION_M = 23;
    @BindView(R.id.player_view) public SimpleExoPlayerView playerView;
    @BindView(R.id.progress) public ProgressBar progressBar;
    @Inject DetailContract.DetailPresenter detailPresenter;

    public static Fragment newInstance(Serializable feedObject, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FEED, feedObject);
        args.putInt(ARG_POSITION, position);
        Fragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        hideActionBar();
        detailPresenter.clearResumePosition();
        detailPresenter.setArguments(getArguments());

    }

    private void hideActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        playerView.requestFocus();

    }

    private ActivityComponent getComponent() {
        return ((MainActivity) getActivity()).getComponent();

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setProgressVisible(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void setPlayerView(SimpleExoPlayer player) {
        playerView.setPlayer(player);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (VERSION_M >= Util.SDK_INT) {
            detailPresenter.onViewAttached(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (VERSION_M >= Util.SDK_INT) {
            detailPresenter.onViewDetached();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (VERSION_M < Util.SDK_INT) {
            detailPresenter.onViewAttached(this);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (VERSION_M < Util.SDK_INT) {
            detailPresenter.onViewDetached();
        }
    }
}
