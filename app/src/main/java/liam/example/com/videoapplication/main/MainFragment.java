package liam.example.com.videoapplication.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.adapter.RecyclerViewAdapter;
import liam.example.com.videoapplication.dagger.components.ActivityComponent;
import liam.example.com.videoapplication.model.Feed;
import liam.example.com.videoapplication.nav.Launcher;
import liam.example.com.videoapplication.utils.RecycleViewClickListener;

import static android.view.View.GONE;

public class MainFragment extends Fragment implements MainContract.MainView {

    public static final int GRID_SIZE = 3;
    @BindView(R.id.progress) public ProgressBar progressBar;
    @BindView(R.id.search_result) public RecyclerView recyclerView;
    @BindView(R.id.swipeContainer) public SwipeRefreshLayout swipe;
    @BindView(R.id.error) public TextView error;

    @Inject MainContract.MainPresenter mainPresenter;
    @Inject RecyclerViewAdapter viewAdapter;
    @Inject Launcher launcher;

    private Feed feed;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainPresenter.onViewAttached(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupViewAdapter();
        swipe.setOnRefreshListener(mainPresenter::fetchDate);
        mainPresenter.fetchDate();
    }

    private void setupViewAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_SIZE));
        recyclerView.setAdapter(viewAdapter);
        recyclerView.addOnItemTouchListener(getListener());
    }

    @NonNull
    private RecyclerView.OnItemTouchListener getListener() {
        return new RecycleViewClickListener(getContext(), (view, position) -> launcher.openDetail(feed, position));
    }

    private void showActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        showActionBar();
        mainPresenter.onViewAttached(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mainPresenter.onViewDetached();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateList(Feed feed) {
        this.feed = feed;
        viewAdapter.setItems(feed);
        viewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setErrorTextVisibil(int visibility) {
        error.setVisibility(visibility);
    }

    private void stopRefreshing() {
        getActivity().runOnUiThread(() -> swipe.setRefreshing(false));
    }

    @Override
    public void setProgressVisible(int visibility) {
        if (GONE == visibility) {
            stopRefreshing();
        }
        progressBar.setVisibility(visibility);
    }

    public ActivityComponent getComponent() {
        return ((MainActivity) getActivity()).getComponent();
    }
}
