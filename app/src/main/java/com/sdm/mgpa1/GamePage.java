package com.sdm.mgpa1;

// Created by TanSiewLan2023
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.os.VibratorManager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GamePage extends FragmentActivity {

    public static GamePage Instance = null;

    private GestureDetector _swipe;
    public static int relativeX;
    public static int relativeY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Instance = this;

        GameView a = new GameView(this);
        setContentView(a); // Surfaceview = GameView

        DisplayMetrics metrics = a.getResources().getDisplayMetrics();
        relativeX = metrics.widthPixels / 1000;
        relativeY = metrics.heightPixels / 1000;

        SwipeMovement.instance = new SwipeMovement(
                (VibratorManager) a.getContext().getSystemService(VIBRATOR_MANAGER_SERVICE));

        _swipe = new GestureDetector(this, SwipeMovement.instance);
        a.setOnTouchListener(touchListener);

        Camera.Instance = new Camera();
    }

    View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        { return _swipe.onTouchEvent(event); }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }
}

