package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class RenderBackground2 implements EntityBase {
    //7. Render a scrolling background
    private Bitmap bmp = null; // bmp is the name of the bitmap to load
    public boolean isDone = false;
    private float xPos = 0, yPos = 0;
    int ScreenWidth, ScreenHeight;
    private Bitmap scaledBmp = null; // Stored the scaled version which is scaled based on Screen width and height.

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }
    @Override
    public void Init(SurfaceView _view)
    {
        // load image from the reso0urce
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.roadbg);
        // screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledBmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
    }

    @Override
    public void Update(float _dt)
    {
        // scrolling background vertically (from top to bottom)
        xPos += _dt * 100; // to deal with the speed of the scrolling
        if (xPos > ScreenHeight) {
            xPos = 0;
        }

        yPos += _dt * 100; // to deal with the speed of the scrolling
        if (yPos > ScreenHeight) {
            yPos = 0;
        }
        //Check if xPos is decreased - screenwidth
        //if so, set xPos to 0, then can start to render the next image.
        //Scrollable background.
    }

    @Override
    public void Render(Canvas _canvas){

        // Render the current image
        _canvas.drawBitmap(scaledBmp, xPos, yPos, null);

// Render the next image to the right
        _canvas.drawBitmap(scaledBmp, xPos + ScreenWidth, yPos, null);

// Render the next image below
        _canvas.drawBitmap(scaledBmp, xPos, yPos + ScreenHeight, null);

// Render the next image diagonally (right and down)
        _canvas.drawBitmap(scaledBmp, xPos + ScreenWidth, yPos + ScreenHeight, null);

        _canvas.drawBitmap(scaledBmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledBmp, xPos - ScreenWidth, yPos, null);
        _canvas.drawBitmap(scaledBmp, xPos, yPos - ScreenHeight, null);
        _canvas.drawBitmap(scaledBmp, xPos - ScreenWidth, yPos - ScreenHeight, null);

    }

    @Override
    public boolean IsInit(){
        return bmp != null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.bg2;}

    public static RenderBackground2 Create(){
        RenderBackground2 result = new RenderBackground2();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.bg2);
        return result;
    }
}
