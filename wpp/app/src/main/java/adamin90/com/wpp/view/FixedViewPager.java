package adamin90.com.wpp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by LiTao on 2015-11-23-22:53.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class FixedViewPager  extends  android.support.v4.view.ViewPager {

public FixedViewPager(Context context) {
        super(context);
        }

public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        }

@Override
public boolean onTouchEvent(MotionEvent ev) {
        try {
        return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
        }
        return false;
        }

@Override
public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
        return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
        }
        return false;
        }
        }
