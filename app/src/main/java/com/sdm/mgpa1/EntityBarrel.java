package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Set;

public class EntityBarrel implements EntityBase, Collidable{
    private boolean isDone = false;
    public float xPos = 0, yPos = 0;
    private boolean isInit = false;

    public static EntityBarrel Instance = null;
    private Bitmap textureBarrel = null;

    private float speed = 100;

    @Override
    public String GetType() {
        return "EntityBarrel";
    }

    public void SetSpeed(int num )
    {
        speed = num;
    }
    public static EntityBarrel Create()
    {
        EntityBarrel result = new EntityBarrel();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_BARREL);
        return result;
    }

    public static EntityBarrel Create(int _layer)
    {
        EntityBarrel result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public void Init(SurfaceView _view) {
        textureBarrel = ResourceManager.Instance.GetBitmap(R.drawable.barrel);
        textureBarrel = Bitmap.createScaledBitmap(textureBarrel,
                textureBarrel.getWidth() / 4, textureBarrel.getHeight() / 4, true);
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.frog);
        Instance = this;
        isInit = true;
        //_gestureDetector = new GestureDetector(_view.getContext(), this);
        //_view.setOnTouchListener(this)
    }
    @Override
    public void Update(float _dt)
    {
//        xPos += speed * _dt;
//
//        if (xPos >= 1000 * GamePage.relativeX){
//            SetIsDone(true);
//        }

        if (yPos + Camera.Instance.GetY() <= 0)
        {
            Log.d("COLLISION", "BARREL PASS AWAYYYYYYY");
            SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        float centerX = xPos - textureBarrel.getWidth() / 2f;
        float centerY = yPos - textureBarrel.getHeight() / 2f;
        _canvas.drawBitmap(textureBarrel, centerX * GamePage.relativeX, centerY + Camera.Instance.GetY() * GamePage.relativeY, null);
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
        return textureBarrel.getWidth() / 2f;
    }

    @Override
    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "PlayerEntity")
        {
        Log.d("COLLIDABLE", "log collide");
        SetIsDone(true);
    }
}

    @Override
    public boolean IsDone()
    {
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
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.BARREL_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
