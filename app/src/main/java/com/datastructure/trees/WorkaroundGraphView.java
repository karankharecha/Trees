package com.datastructure.trees;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.otaliastudios.zoom.ZoomEngine;
import de.blox.graphview.GraphView;

public class WorkaroundGraphView extends GraphView {
    int[] viewLocation = new int[2];

    public WorkaroundGraphView(Context context) {
        super(context);
    }

    public WorkaroundGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkaroundGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onUpdate(ZoomEngine helper, Matrix matrix) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && getChildCount() > 0) {
            final View childAt = getChildAt(0);
            childAt.getLocationInWindow(viewLocation);
            matrix.postTranslate(viewLocation[0], viewLocation[1]);
        }
        super.onUpdate(helper, matrix);
    }
}
