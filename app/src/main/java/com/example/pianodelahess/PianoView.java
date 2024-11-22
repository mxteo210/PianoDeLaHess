package com.example.pianodelahess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NB = 14;
    private Paint black, gray, white;
    private ArrayList<Key> whites = new ArrayList<>();
    private ArrayList<Key> blacks = new ArrayList<>();
    private int keyWidth, height;
    private AudioSoundPlayer soundPlayer;

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        gray = new Paint();
        gray.setColor(Color.GRAY);
        gray.setStyle(Paint.Style.FILL);
        soundPlayer = new AudioSoundPlayer(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NB;
        height = h;
        int count = 15;

        for (int i = 0; i < NB; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NB - 1) {
                right = w;
            }

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(rect, i + 1));

            if (i != 0  &&   i != 3  &&  i != 7  &&  i != 10) {
                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * height);
                blacks.add(new Key(rect, count));
                count++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Key k : whites) {
            canvas.drawRect(k.rect, k.down ? gray : white);
        }

        for (int i = 1; i < NB; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, black);
        }

        for (Key k : blacks) {
            canvas.drawRect(k.rect, k.down ? gray : black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // Utilise `getActionMasked` pour gérer multi-touches
        int pointerIndex = event.getActionIndex(); // Indique quel pointeur est en action

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_MOVE) {
            // Gère les pointeurs actifs
            for (int i = 0; i < event.getPointerCount(); i++) {
                float x = event.getX(i);
                float y = event.getY(i);

                Key k = keyForCoords(x, y);
                if (k != null && !k.isPlaying) {
                    k.down = true;
                    k.isPlaying = true;
                    soundPlayer.playNote(k.sound);
                    invalidate();
                }
            }
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            // Relâche seulement la touche du pointeur actif
            float x = event.getX(pointerIndex);
            float y = event.getY(pointerIndex);

            Key k = keyForCoords(x, y);
            if (k != null) {
                k.down = false;
                k.isPlaying = false;
            }

            // Réinitialise toutes les touches si nécessaire
            if (action == MotionEvent.ACTION_UP) {
                resetKeys();
            }
        }

        return true;
    }

    private Key keyForCoords(float x, float y) {
        for (Key k : blacks) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        for (Key k : whites) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        return null;
    }

    private void resetKeys() {
        ArrayList<Key> allKeys = new ArrayList<>(whites);
        allKeys.addAll(blacks);

        for (Key k : allKeys) {
            k.down = false;
            k.isPlaying = false; // Réinitialise l'état de lecture
        }
        invalidate();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

    // Classe Key modifiée avec la propriété isPlaying
    private static class Key {
        RectF rect;
        int sound;
        boolean down;
        boolean isPlaying; // Indique si la note est en cours de lecture

        Key(RectF rect, int sound) {
            this.rect = rect;
            this.sound = sound;
            this.down = false;
            this.isPlaying = false;
        }
    }
}

