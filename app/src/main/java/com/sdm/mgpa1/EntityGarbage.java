package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import androidx.constraintlayout.helper.widget.Layer;

public class EntityGarbage implements EntityBase, Collidable
{
    public float xPos, yPos;
    private Bitmap bmp = null;
    private boolean isDone = false;
    private  boolean isInit = false;
    @Override
    public String GetType() {
        return "EntityGarbage";
    }
    public static EntityGarbage Create()
    {
        EntityGarbage result = new EntityGarbage();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_GARBAGE);
        return result;
    }

    public static EntityGarbage Create(int _layer)
    {
        EntityGarbage result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }
    @Override
    public void Init(SurfaceView _view)
    {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.garbage);
        bmp = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / 4,bmp.getHeight() / 4,true);
    }

    @Override
    public void Update(float _dt)
    {
        if (yPos + Camera.Instance.GetY() <= 0)
        {
            Log.d("GARBAGE", "DELETED INNIT");
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
    public float GetRadius()
    {
        return (bmp.getWidth() / 1.5f);
    }
    @Override
    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "PlayerEntity")
        {
            SetIsDone(true);
        }
    }

    @Override
    public boolean IsDone()
    {
        return isDone;
    }
    public int GetRenderLayer() {
        return LayerConstants.GARBAGE_LAYER;
    }
    @Override
    public void SetIsDone(boolean _isDone)
    {
        if (_isDone)
        {
            //isDone = _isDone;
            EntityManager.Instance.RemoveEntity(this);
        }
    }
    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {}
    @Override
    public ENTITY_TYPE GetEntityType() {return null;}
}
