package liam.example.com.videoapplication;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static junit.framework.Assert.assertEquals;

public class RecyclerViewPopulatedAssertion implements ViewAssertion {
    final int size;

    public RecyclerViewPopulatedAssertion(int size) {
        this.size = size;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertEquals(adapter.getItemCount(), size);
    }
}