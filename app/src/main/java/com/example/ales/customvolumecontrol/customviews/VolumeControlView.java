package com.example.ales.customvolumecontrol.customviews;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.ales.customvolumecontrol.R;
import com.example.ales.customvolumecontrol.VolumeControlActivity;

public class VolumeControlView extends View {

    Point centerOfCanvas;
    Rect rect;
    private int rectangleWidth = 0;
    private int rectangleHeight = 0;
    private int coordinateX;
    private int coordinateY;
    private int rectangleSpaceBetween;
    private int controlVolume;
    private int controlScale;
    private String volumeString = null;
    private int controlColor;
    private Paint paintFillRectangle, paintEmptyRectangle, paintText;

    /**
     * Constructor default
     * @param context
     */
    public VolumeControlView(Context context) {
        super(context);
        initView(context);
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }*/

    /**
     * Constructor with Attributes
     * @param context
     * @param attrs
     */
    public VolumeControlView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttributesArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.VolumeControlView,
                0, 0);

        try {
            controlVolume = styledAttributesArray.getInt(R.styleable.VolumeControlView_controlVolume, 0);
            controlScale = styledAttributesArray.getInt(R.styleable.VolumeControlView_controlScale, 0);
            controlColor = styledAttributesArray.getColor(R.styleable.VolumeControlView_controlColor, context.getResources().getColor(R.color.colorPrimary));
        } finally {
            styledAttributesArray.recycle();
        }

        initView(context);
    }

    /**
     * Inits layout and widgets.
     * @param context current context.
     */
    private void initView(Context context) {
        centerOfCanvas = new Point(getWidth() / 2, 150);

        paintFillRectangle = new Paint();
        paintFillRectangle.setStyle(Paint.Style.FILL);
        paintFillRectangle.setColor(controlColor);

        paintEmptyRectangle = new Paint();
        paintEmptyRectangle.setStyle(Paint.Style.FILL);
        paintEmptyRectangle.setColor(Color.GRAY);

        paintText = new Paint();
        paintText.setColor(Color.BLACK);

        int spSize = 15;
        float scaledSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spSize, getResources().getDisplayMetrics());
        paintText.setTextSize(scaledSizeInPixels);

        coordinateX = centerOfCanvas.x + (rectangleWidth / 2);
        coordinateY = centerOfCanvas.y - (rectangleHeight / 2);
        rect = new Rect(coordinateX, coordinateY, coordinateX + rectangleWidth, coordinateY + rectangleHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectangleWidth = getWidth();
        rectangleSpaceBetween = 5;
        rectangleHeight = Math.round(getHeight() / controlScale) - rectangleSpaceBetween;

        int coordinateX = centerOfCanvas.x;
        int coordinateY = 0;

        int numEmptyRectangles = controlScale - ((controlScale * controlVolume) / 100);
        int numFullRectangles = controlScale - numEmptyRectangles;

        for (int i = 1; i <= (numEmptyRectangles); i++) {
            rect.set(coordinateX, coordinateY, coordinateX + rectangleWidth, coordinateY + rectangleHeight);
            canvas.drawRect(rect, paintEmptyRectangle);
            coordinateY = coordinateY + rectangleHeight + rectangleSpaceBetween;
        }

        // draw full rectangles
        for (int i = 1; i <= numFullRectangles; i++) {
            rect.set(coordinateX, coordinateY, coordinateX + rectangleWidth, coordinateY + rectangleHeight);
            canvas.drawRect(rect, paintFillRectangle);
            coordinateY = coordinateY + rectangleHeight + rectangleSpaceBetween;
        }

        volumeString = getResources().getString(R.string.volume_control_set, controlVolume);
        //canvas.drawText(volumeString, 10, getHeight(), paintText);

        ((VolumeControlActivity)getContext()).setTextVolume(volumeString);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pressedX = (int) event.getX();
        int pressedY = (int) event.getY();

        float scale = getHeight() - pressedY;
        float volume = scale / getHeight();
        int intVol = Math.round(volume * 100);
        intVol = Math.max(0, intVol);
        intVol = Math.min(100, intVol);

        Log.d("VOLUME", String.format("scale: %.2f volume %.2f intvol: %d", scale, volume, intVol));
        Log.d("COORDINATES", String.format("coordinateX: %d, CoordinateY %d", pressedX, pressedY));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setVolume(intVol);
                break;

            case MotionEvent.ACTION_MOVE:
                setVolume(intVol);
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public int getControlVolume() {
        return controlVolume;
    }

    public int getControlScale() {
        return controlScale;
    }

    public int getControlColor() {
        return controlColor;
    }

    /**
     * Set volume in percent
     * @param volume value between 0 and 100 %
     */
    public void setVolume(int volume) {
        Log.d("VOLUME", "volume: " + volume);
        controlVolume = volume;
        invalidate();
    }

    /**
     * Set scale in number
     * @param scale value between 0 and 100
     */
    public void setScale(int scale) {
        controlScale = scale;
        controlScale = Math.max(0, controlScale);
        controlScale = Math.min(100, controlScale);
        invalidate();
    }

    /**
     * Set color for volume control
     * @param color
     */
    public void setColor(int color) {
        controlColor = color;
        paintFillRectangle.setColor(controlColor);
        invalidate();
    }
}
