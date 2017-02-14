package liam.example.com.videoapplication;


import android.content.Context;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import liam.example.com.videoapplication.detail.DetailContract;
import liam.example.com.videoapplication.detail.DetailPresenterImpl;
import liam.example.com.videoapplication.model.Feed;

import static liam.example.com.videoapplication.detail.DetailContract.DetailPresenter.ARG_FEED;
import static liam.example.com.videoapplication.detail.DetailContract.DetailPresenter.ARG_POSITION;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    DetailPresenterImpl detailPresenter;
    @Mock Context mockContext;
    @Mock MediaSource mockMediaSource;
    @Mock SimpleExoPlayer mockPLayer;
    @Mock DetailContract.DetailView mockView;
    @Mock Bundle mockBundle;
    @Mock Feed testFeed;

    @Before
    public void setUp() throws Exception {
        detailPresenter = spy(new DetailPresenterImpl(mockContext));
        doReturn(testFeed).when(mockBundle).getSerializable(eq(ARG_FEED));
        doReturn(1).when(mockBundle).getInt(eq(ARG_POSITION));
        detailPresenter.setArguments(mockBundle);

    }
    
    @Test
    public void playerShouldPrepareOnViewAttached(){
        doReturn(mockMediaSource).when(detailPresenter).getMediaSource();
        doReturn(mockPLayer).when(detailPresenter).getPlayere();
        detailPresenter.onViewAttached(mockView);
        verify(mockPLayer).prepare(mockMediaSource,false, false);
    }


}