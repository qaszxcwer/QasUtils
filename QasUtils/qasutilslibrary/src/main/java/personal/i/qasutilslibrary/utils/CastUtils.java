package personal.i.qasutilslibrary.utils;

/**
 * 作者：qaszxcwer
 * 日期：2020/7/16
 *
 * 强制转化工具类
 */
public class CastUtils {
    public static <T extends Object> T cast(Object object, Class<T> clazz) {
        if (object != null) {
            try {
                return clazz.cast(object);
            }catch (ClassCastException e) {
                // do nothing
            }
        }
        return null;
    }
}
