package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import androidx.constraintlayout.helper.widget.Layer;

public class EntityLog implements EntityBase, Collidable
{
    public float xPos, yPos;
    private Bitmap bmp = null;
    private boolean isDone = false;
    private  boolean isInit = false;
    @Override
    public String GetType() {
        return "EntityLog";
    }

    public static EntityLog Create()
    {
        EntityLog result = new EntityLog();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_GARBAGE);
        return result;
    }

    public static EntityLog Create(int _layer)
    {
        EntityLog result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public void Init(SurfaceView _view)
    {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.log);
        bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 5, bmp.getHeight() / 5,true);
    }

    @Override
    public void Update(float _dt)
    {
        if (yPos + Camera.Instance.GetY() <= 0)
        {
            Log.d("LOG", "DELETED INNIT");
            SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        float centerX = xPos - bmp.getWidth() / 2f;
        float centerY = yPos - bmp.getHeight() / 2f;
        _canvas.drawBitmap(bmp, centerX * GamePage.relativeX, centerY + Camera.Instance.GetY() * GamePage.relativeY, null);
    }
    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth() / 5;
    }

    @Override
    public void OnHit(Collidable _other)
    {

    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        if (isDone)
        {
            EntityManager.Instance.RemoveEntity(this);
        }
//        isDone = _isDone;
    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.LOG_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
