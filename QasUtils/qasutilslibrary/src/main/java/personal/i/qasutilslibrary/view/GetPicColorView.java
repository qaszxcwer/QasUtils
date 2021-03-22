package personal.i.qasutilslibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import personal.i.qasutilslibrary.R;

/**
 * 从图片中取色
 *
 * 作者：qaszxcwer
 * 日期：2021/3/22
 */
public class GetPicColorView extends View implements View.OnTouchListener{
    /**
     * 圆环粗细
     */
    private static final int RING_WIDTH = 16;

    /**
     * 圆环粗细的一半
     */
    private static final int RING_WIDTH_HALF = RING_WIDTH / 2;

    /**
     * 圆环粗细的两倍
     */
    private static final int RING_WIDTH_DOUBLE = RING_WIDTH * 2;

    /**
     * 圆环扫过的角度
     */
    private static final int RAING_ANGLE = 360;

    private final int resBitmap = R.drawable.icon_cross_line;

    private Context mContext;

    private float downX;

    private float downY;

    private int width;

    private int height;

    private int widthMax;

    private int heightMax;

    private Paint paint;

    private Canvas mCanvas;

    private RectF oval;

    private ICrossPosChange posChange;

    public GetPicColorView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOnTouchListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(RING_WIDTH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resBitmap);
        // 十字线大小比view本身小一点，空出圆环的位置
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(bitmap, width - RING_WIDTH_DOUBLE, height - RING_WIDTH_DOUBLE, true),
                RING_WIDTH, RING_WIDTH, null);
        this.mCanvas = canvas;
        onPosChange();
    }

    /**
     * 画圆环颜色
     * @param color 颜色
     */
    public void drawColorRaing(int color) {
        paint.setColor(color);
        // 画布缩小一半的描边宽度，避免描边超出View
        oval = new RectF();
        oval.left = RING_WIDTH_HALF;
        oval.top = RING_WIDTH_HALF;
        oval.right = width - RING_WIDTH_HALF;
        oval.bottom = height - RING_WIDTH_HALF;
        mCanvas.drawArc(oval, 0, RAING_ANGLE, false, paint);
    }

    /**
     * 设置位置改变的回调
     * @param iCrossPosChange 回调
     */
    public void setOnPosChangeListener(ICrossPosChange iCrossPosChange) {
        this.posChange = iCrossPosChange;
    }

    /**
     * 设置中心点最大活动范围
     *
     * @param width 矩形宽
     * @param height 矩形高
     */
    public void setMaxSize(int width, int height) {
        this.widthMax = width;
        this.heightMax = height;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = motionEvent.getX() - downX;
                float moveY = motionEvent.getY() - downY;
                int l = (int) (getLeft() + moveX);
                int t = (int) (getTop() + moveY);
                int r = l + width;
                int b = t + height;
                int centerX = (int) (l + width / 2);
                int centerY = (int) (t + height / 2);
                if (centerX >= widthMax || centerX <= 0 || centerY >= heightMax || centerY <= 0) {
                    // 禁止超过最大范围
                    return true;
                }
                this.layout(l, t, r, b);
                onPosChange();
                break;
            default:
                break;
        }
        return true;
    }

    private void onPosChange() {
        if (posChange != null) {
            int centerX = (int) (getX() + width / 2);
            int centerY = (int) (getY() + height / 2);
            posChange.onPosChanged(centerX, centerY);
        }
    }

    /**
     * 位置改变的接口
     */
    public interface ICrossPosChange {
        /**
         * 位置改变之后，自身中间位置在父布局的坐标
         * @param centerX x
         * @param centerY y
         */
        void onPosChanged(int centerX, int centerY);
    }
}
