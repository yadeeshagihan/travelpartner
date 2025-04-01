package com.tugaybakay.travelpartner;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    private Paint paint;
    private float direction = 0;

    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 10;

        // Draw circle
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw direction text
        String text = "Direction: " + direction;
        canvas.drawText(text, centerX - paint.measureText(text) / 2, centerY + radius + 50, paint);
    }

    public void setDirection(float direction) {
        this.direction = direction;
        invalidate();
    }
}

