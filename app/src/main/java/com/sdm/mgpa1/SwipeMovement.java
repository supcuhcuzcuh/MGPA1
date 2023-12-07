package com.sdm.mgpa1;

import android.content.Context;
import android.gesture.Gesture;
import android.os.VibrationEffect;
import android.os.VibratorManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeMovement extends GestureDetector.SimpleOnGestureListener {
    public static SwipeMovement instance;
    public GenericSubscription<Direction> onSwipe;
    public GenericSubscription<V2> onTapDown;
    public GenericSubscription<V2> onTapUp;
    public GenericSubscription<V2> onTwoTap;

    public SwipeMovement(VibratorManager v)
    {
        onSwipe = new GenericSubscription<>();
        onTapDown = new GenericSubscription<>();
        onTapUp = new GenericSubscription<>();
        onTwoTap = new GenericSubscription<>();
        _vib = v;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        Direction direction = getDirection(x1,y1,x2,y2);
        onSwipe.invoke(direction);
        Log.d("GESTURES", "SWIPED");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent a)
    {
        Log.d("GESTURES", "TAP DOWN");
        onTapDown.invoke(new V2(a.getX(),a.getY()));
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent a)
    {
        Log.d("GESTURES", "TAP UP");
        onTapUp.invoke(new V2(a.getX(),a.getY()));
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent a)
    {
        Log.d("GESTURES", "TWO TAP");
        onTwoTap.invoke(new V2(a.getX(),a.getY()));
        return true;
    }

    private VibratorManager _vib;

    public void vibrate(long milli, int amp)
    {
        _vib.getDefaultVibrator().vibrate(VibrationEffect.createOneShot(milli,amp));
    }

    // HELPER FUNCTIONS

    public Direction getDirection(float x1, float y1, float x2, float y2)
    {
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.fromAngle(angle);
    }

    public double getAngle(float x1, float y1, float x2, float y2)
    {
        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }

    public enum Direction{
        up,
        down,
        left,
        right;
        public static Direction fromAngle(double angle)
        {
            if(inRange(angle, 45, 135))
                return Direction.up;
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360))
                return Direction.right;
            else if(inRange(angle, 225, 315))
                return Direction.down;
            else
                return Direction.left;
        }

        private static boolean inRange(double angle, float init, float end)
        { return (angle >= init) && (angle < end); }
    }
}
