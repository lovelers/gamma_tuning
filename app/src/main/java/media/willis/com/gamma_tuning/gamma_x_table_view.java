package media.willis.com.gamma_tuning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class gamma_x_table_view extends View {
    int[] mXTable;

    public gamma_x_table_view(Context context) {
        super(context);
    }
    public gamma_x_table_view(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public gamma_x_table_view(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private int min(int a, int b) {
        return (a > b) ? b : a;
    }

    public void setXTable(int[] xTable) {
        mXTable = xTable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        int width = getWidth() - left - right;
        int height = getHeight() - top - bottom;
        canvas.save();
        canvas.scale(1, -1, width / 2, height / 2);
        int drawSize = min(width, height);
        drawSize = drawSize / 32 * 32;
        Rect drawRect = new Rect(left, top, drawSize, drawSize);
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        canvas.drawRect(drawRect, rectPaint);

        PointF pointf = new PointF(mXTable[0] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize + drawRect.left, mXTable[0] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize + drawRect.top);
        Paint xPaint = new Paint();
        xPaint.setColor(Color.RED);
        for (int i = 1; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            PointF tmp = new PointF(mXTable[i] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize + drawRect.left, mXTable[i] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize + drawRect.top);
            canvas.drawLine(pointf.x, pointf.y, tmp.x, pointf.y, xPaint);
            pointf = tmp;
        }
        canvas.restore();
        super.onDraw(canvas);
    }

}
