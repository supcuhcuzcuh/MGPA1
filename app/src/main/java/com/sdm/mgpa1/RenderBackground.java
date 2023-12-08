package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase {
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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.sea);
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
        yPos += _dt * 500; // to deal with the speed of the scrolling
        if (yPos > ScreenHeight) {
            yPos = 0;
        }
        //Check if xPos is decreased - screenwidth
        //if so, set xPos to 0, then can start to render the next image.
        //Scrollable background.
    }

    @Override
    public void Render(Canvas _canvas){
        //We draw 2 images of the same kind.
        //Once the 1st image reached 0 based on scrolling from my right to my left.
        //draw the next image.
        //Draw = render
        _canvas.drawBitmap(scaledBmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledBmp, xPos, yPos -   ScreenHeight, null);
        //xPos will change and yPos which is set as 0 will be no change.
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
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_DEFAULT;}

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
