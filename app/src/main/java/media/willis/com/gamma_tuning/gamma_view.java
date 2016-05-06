package media.willis.com.gamma_tuning;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class gamma_view extends View {

    private float mGammaExponent;
    private int mGammaLowOffset;
    private int mGammaHighOffset;
    private boolean mSGammaEnabled = false;
    private float[] mSGamma;
    private int[] mGammaTableX;
    public gamma_view(Context context) {
        super(context);
    }
    public gamma_view(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public gamma_view(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setInfo(float gammaExponent, int gammaLowOffset, int gammaHighOffset) {
        mGammaExponent = gammaExponent;
        mGammaLowOffset = gammaLowOffset;
        mGammaHighOffset = gammaHighOffset;
    }

    public void setSGammaInfo(boolean sGammaEnabled, float[] sGamma) {
        mSGammaEnabled = sGammaEnabled;
        mSGamma = sGamma;
    }

    public void setmGammaTableX(int [] gammaTablex) {
        mGammaTableX = gammaTablex;
    }

    private int min(int a, int b) {
        return (a > b) ? b : a;
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
        canvas.scale(1, -1, width / 2, height /2);
        int drawSize = min(width, height);
        drawSize = drawSize / 32 * 32;
        Rect drawRect = new Rect(left, top, drawSize, drawSize);
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        canvas.drawRect(drawRect, rectPaint);

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        int distance = (drawSize) / Gamma.GAMMA_X_TABLE_COUNT;
        for (int i = 0; i <= Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            canvas.drawLine(i * distance + drawRect.left, drawRect.top, i * distance + drawRect.left, drawRect.bottom, linePaint);
            canvas.drawLine(drawRect.left, i * distance + drawRect.top, drawRect.right, i * distance + drawRect.top, linePaint);
        }


        float[] gammaY = new float[Gamma.GAMMA_X_TABLE_COUNT];
        float[] gammaYWithSGamma = new float[Gamma.GAMMA_X_TABLE_COUNT];
        int gammaTotalSize = Gamma.GAMMA_X_VALUE_MAX + mGammaHighOffset;
        for (int i = 0; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            gammaY[i] = (float)Math.pow(mGammaTableX[i] / (float) Gamma.GAMMA_X_VALUE_MAX, 1 / mGammaExponent) * gammaTotalSize + mGammaLowOffset;
            if (gammaY[i] < 0) gammaY[i] = 0;
            if (mSGammaEnabled == true) {
                gammaYWithSGamma[i] = (gammaY[i] * mSGamma[i]);
                if (gammaYWithSGamma[i] < 0) gammaYWithSGamma[i] = 0;
            }
        }

        Paint gammaYPaint = new Paint();
        Paint vetPaint = new Paint();
        vetPaint.setColor(Color.RED);
        gammaYPaint.setColor(Color.GREEN);
        PointF sPoint = new PointF();
        sPoint.set(drawRect.left + mGammaTableX[0] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize, drawRect.top + gammaY[0] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize);

        for (int i = 1; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            PointF tmp = new PointF();
            tmp.set(drawRect.left + mGammaTableX[i] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize, drawRect.top + gammaY[i] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize);
            canvas.drawLine(sPoint.x, sPoint.y, tmp.x, tmp.y, gammaYPaint);
            canvas.drawCircle(sPoint.x, sPoint.y, 5, vetPaint);
            sPoint = tmp;
        }
        if (mSGammaEnabled == true) {
            PointF sPoint1 = new PointF();
            sPoint1.set(drawRect.left + mGammaTableX[0] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize, drawRect.top + gammaYWithSGamma[0] /(float) Gamma.GAMMA_X_VALUE_MAX * drawSize);
            Paint gammaYSGPaint = new Paint();
            gammaYSGPaint.setColor(Color.BLUE);
            for (int i = 1; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
                PointF tmp = new PointF();
                tmp.set(drawRect.left + mGammaTableX[i] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize, drawRect.top + gammaYWithSGamma[i] / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize);
                canvas.drawLine(sPoint1.x, sPoint1.y, tmp.x, tmp.y, gammaYSGPaint);
                canvas.drawCircle(sPoint1.x, sPoint1.y, 5, vetPaint);
                sPoint1 = tmp;
            }
        }
        Paint smoothGamma = new Paint();
        smoothGamma.setColor(Color.RED);
        float lowOffset = mGammaLowOffset / (float) Gamma.GAMMA_X_VALUE_MAX * drawSize;
        float totalSize = (mGammaHighOffset + Gamma.GAMMA_X_VALUE_MAX) / (float)Gamma.GAMMA_X_VALUE_MAX * drawSize;

        for (int i = 0; i <= drawSize; ++i) {
            float x = i + drawRect.top;
            float y = (float) Math.pow(i / (float) drawSize, 1 / mGammaExponent) * totalSize + drawRect.left + lowOffset;
            if (x < drawRect.top) x = drawRect.top;
            if (y > drawRect.bottom) y = drawRect.bottom;
            canvas.drawCircle(x, y, 1, smoothGamma);
        }

        canvas.restore();
        super.onDraw(canvas);
    }

}
