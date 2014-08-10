
package com.xian.xingyu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AddGridView extends GridView {

    public AddGridView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public AddGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
