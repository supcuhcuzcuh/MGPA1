package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class EntitySpeedPowerUp implements EntityBase, Collidable
{
    public float xPos, yPos;
    private Bitmap bmp = null;
    private boolean isDone = false;
    @Override
    public String GetType() {
        return "MovementPowerUp";
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.lowercdpowerup);
        bmp = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / 3 ,bmp.getHeight() / 3,true);
    }

    @Override
    public void Update(float _dt) {
        if (yPos + Camera.Instance.GetY() <= 0)
        {
            Log.d("SPEED POWER UP", "DELETED INNIT");
            SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, xPos * GamePage.relativeX, yPos + Camera.Instance.GetY() * GamePage.relativeY, null);
    }
    public static EntitySpeedPowerUp Create()
    {
        EntitySpeedPowerUp result = new EntitySpeedPowerUp();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_GARBAGE);
        return result;
    }

    public static EntitySpeedPowerUp Create(int _layer)
    {
        EntitySpeedPowerUp result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() { return yPos;}

    @Override
    public float GetRadius() {
        return (bmp.getWidth() / 2) * GamePage.relativeX;
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
    public boolean IsDone() {
        return isDone;
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
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SPEEDPOWERUP_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
