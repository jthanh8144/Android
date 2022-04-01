package com.example.piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PianoView extends View {
    public static final int NUMBER_KEYS = 14;
    private Paint black, white, yellow, blackStroke;
    private ArrayList<Key> blacks, whites;
    private int keyWidth, keyHeight;

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        blackStroke = new Paint();
        blackStroke.setColor(Color.BLACK);
        blackStroke.setStyle(Paint.Style.STROKE);
        blackStroke.setStrokeWidth(2f);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        blacks = new ArrayList<Key>();
        whites = new ArrayList<Key>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NUMBER_KEYS;
        keyHeight = h;

        int count = 15;
        for (int i = 0; i < NUMBER_KEYS; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;
            RectF rectF = new RectF(left, 0, right, keyHeight);
            whites.add(new Key(i + 1, rectF));

            if (i != 0 && i != 3 && i != 7 && i != 10) {
                rectF = new RectF((float) (i - 1) * keyWidth + 0.75f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * keyHeight);
                blacks.add(new Key(count++, rectF));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Key k : whites) {
            canvas.drawRect(k.rect, k.isDown ? yellow : white);
        }
        for (int i = 1; i < NUMBER_KEYS; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, keyHeight, blackStroke);
        }

        for (Key k : blacks) {
            canvas.drawRect(k.rect, k.isDown ? yellow : black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownActivie = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            for (Key k : whites) {
                if (k.rect.contains(x, y)) {
                    k.isDown = isDownActivie;
                }
            }
        }
        invalidate();
        return true;
    }
}
