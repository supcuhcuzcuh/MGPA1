package com.sdm.mgpa1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class WinTextEntity implements EntityBase
{
    // Paint Object
    Paint paint = new Paint();
    // Variable to be used to set colors
    private int red = 0, green = 0, blue = 0; // Colors range from 0 to 255

    // We want to use our own font type,
    protected Typeface myfont;
    private boolean isDone = false;
    private boolean isInit = false;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // Load font
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "LatoBlack.ttf");

        isInit = true;
    }

    @Override
    public void Update(float _dt)
    {
    }

    @Override
    public void Render(Canvas _canvas)
    {
        // Set font color and size
        paint.setColor(Color.YELLOW);
        paint.setTextSize(180);

        // Draw FPS
        paint.setTypeface(myfont);
        _canvas.drawText("YOU WIN!", 500 * GamePage.relativeX, 500 * GamePage.relativeY, paint);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.UI_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static WinTextEntity Create()
    {
        WinTextEntity result = new WinTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_TEXT;}
}
