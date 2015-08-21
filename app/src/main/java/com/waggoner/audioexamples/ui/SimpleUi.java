package com.waggoner.audioexamples.ui;

import android.content.Context;
import android.view.View;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public interface SimpleUi {

    View createUI(Context ctx);

    void destoryUi();
}
