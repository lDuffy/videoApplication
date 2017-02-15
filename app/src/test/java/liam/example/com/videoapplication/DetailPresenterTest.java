package liam.example.com.videoapplication;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import liam.example.com.videoapplication.detail.DetailContract;
import liam.example.com.videoapplication.detail.DetailPresenterImpl;
import liam.example.com.videoapplication.player.IExoPlayer;

import static liam.example.com.videoapplication.detail.DetailContract.DetailPresenter.ARG_POSITION;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    DetailPresenterImpl detailPresenter;

    @Mock DetailContract.DetailView mockView;
    @Mock IExoPlayer mockExoPlayer;
    @Mock Context mockContext;
    @Mock Bundle mockBundle;
    @Mock List<Uri> list;

    @Before
    public void setUp() throws Exception {
        detailPresenter = spy(new DetailPresenterImpl(mockContext, mockExoPlayer));
        doReturn(1).when(mockBundle).getInt(eq(ARG_POSITION));
        doReturn(list).when(detailPresenter).getList();
        detailPresenter.setArguments(mockBundle);

    }
    
    @Test
    public void playerShouldPrepareOnViewAttached(){
        doReturn(true).when(mockExoPlayer).isPlayerNull();
        detailPresenter.onViewAttached(mockView);
        verify(mockExoPlayer).prepare();
    }


}