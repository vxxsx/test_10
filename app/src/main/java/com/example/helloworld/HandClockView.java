package com.example.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class HandClockView extends View implements Runnable {
    private static final String NAMESPACE = "http://www.pro.dev.com/common";
    private static final String CLOCK_FACE="colckFace";
    private static final String HAND_CENTER_WIDTH_SCALE = "handCenterWidthScale";
    private static final String HAND_CENTER_HEIGHT_SCALE = "handCenterHeightScale";
    private static final String SCALE = "scale";
    private static final String HOUR_HAND_SIZE = "hourHandSize";
    private static final String MINUTE_HAND_SIZE = "minuteHandSize";
    private Bitmap clockFace;
    private int clockFaceId;
    private float centerX;
    private float centerY;
    private float handCenterWidthScale;
    private float handCenterHeightScale;
    private float scale;
    private int hourHandSize;
    private int minuteHandSize;
    private Handler handler = new Handler();


    public HandClockView(Context context) {
        super(context);
    }
    public HandClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public HandClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        clockFaceId = attrs.getAttributeResourceValue(NAMESPACE, CLOCK_FACE, 0);
        Log.e("zhang.xiao", "clockFaceId=" + clockFaceId);
        if (clockFaceId > 0) {
            clockFace = BitmapFactory.decodeResource(getResources(), clockFaceId);
        }
        Log.e("zhang.xiao", "clockFace is null " + String.valueOf(clockFace == null));
        handCenterWidthScale = attrs.getAttributeFloatValue(NAMESPACE, HAND_CENTER_WIDTH_SCALE, clockFace.getWidth() / 2);
        handCenterHeightScale = attrs.getAttributeFloatValue(NAMESPACE, HAND_CENTER_HEIGHT_SCALE, clockFace.getHeight() / 2);
        scale = attrs.getAttributeFloatValue(NAMESPACE, SCALE, 0);
        hourHandSize = (int) (scale * attrs.getAttributeIntValue(NAMESPACE, HOUR_HAND_SIZE, 0));
        minuteHandSize = (int) (scale * attrs.getAttributeIntValue(NAMESPACE, MINUTE_HAND_SIZE, 0));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(clockFace.getWidth() * scale), (int)(clockFace.getHeight() * scale));
    }
    @Override
    public void run() {
        invalidate();
        handler.postDelayed(this, 60 * 1000);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Rect src = new Rect();
        src.left = 0;
        src.top = 0;
        src.right = clockFace.getWidth();
        src.bottom = clockFace.getHeight();
        Rect target = new Rect();
        target.left = 0;
        target.top = 0;
        target.right = (int) (src.right * scale);
        target.bottom = (int) (src.bottom * scale);
        canvas.drawBitmap(clockFace, src, target, paint);
        centerX = clockFace.getWidth() * handCenterWidthScale * scale;
        centerY = clockFace.getHeight() * handCenterHeightScale * scale;
        canvas.drawCircle(centerX, centerY, 5, paint);
        paint.setStrokeWidth(3);
        Calendar cal = Calendar.getInstance();
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        int second = cal.get(Calendar.SECOND);
        Log.e("zhang.xiao", "hour=" + hour);
        Log.e("zhang.xiao", "minute=" + minute);
        double minuteRadian = Math.toRadians((360 - ((minute * 6) - 90)) % 360);
        double hourRadian = Math.toRadians(((360 - ((hour * 30) - 90)) % 360) - (30 * minute / 60));
        canvas.drawLine(centerX, centerY, (int) (centerX + minuteHandSize * Math.cos(minuteRadian)),
                (int) (centerY - Math.sin(minuteRadian) * minuteHandSize), paint);
        paint.setStrokeWidth(4);
        canvas.drawLine(centerX, centerY, (int) (centerX + hourHandSize * Math.cos(hourRadian)),
                (int) (centerY - Math.sin(hourRadian) * hourHandSize), paint);
        handler.postDelayed(this, (60 - second) * 1000);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("zhang.xiao", "onDetachedFromWindow");
    }
}
