package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Максим on 01.06.2018.
 */

public class SwipeManager implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progress;
    private FuncVoid funcVoid;

    public SwipeManager(SwipeRefreshLayout swipeRefresh, ProgressBar progress, FuncVoid funcVoid) {
        this.swipeRefresh = swipeRefresh;
        this.progress = progress;
        this.funcVoid = funcVoid;
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        progress.setVisibility(View.VISIBLE);
        funcVoid.action();
        swipeRefresh.setRefreshing(false);
    }
}
