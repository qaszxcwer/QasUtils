package personal.qasutils.utils;

import android.view.View;

/**
 * 作者：qaszxcwer
 * 日期：2020/7/14
 */
public class ViewUtils {
    public static <T extends View> T findViewById(View rootView, int id) {
        if (rootView == null) {
            return null;
        }
        T t =  rootView.findViewById(id);
        return t;
    }
}
