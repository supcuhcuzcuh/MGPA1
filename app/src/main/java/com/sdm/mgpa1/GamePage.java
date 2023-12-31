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
    public static GameView currentSceneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Instance = this;

        currentSceneView = new GameView(this);
        setContentView(currentSceneView); // Surfaceview = GameView

        DisplayMetrics metrics = currentSceneView.getResources().getDisplayMetrics();
        relativeX = metrics.widthPixels / 1000;
        relativeY = metrics.heightPixels / 1000;

        SwipeMovement.Instance = new SwipeMovement(
                (VibratorManager) currentSceneView.getContext().getSystemService(VIBRATOR_MANAGER_SERVICE));

        _swipe = new GestureDetector(this, SwipeMovement.Instance);
        currentSceneView.setOnTouchListener(touchListener);

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

