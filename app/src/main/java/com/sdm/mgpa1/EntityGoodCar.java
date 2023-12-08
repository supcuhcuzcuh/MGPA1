package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.SurfaceView;

import java.util.Set;

public class EntityGoodCar implements EntityBase, Collidable{
    private Bitmap _bitmap = null;

    private boolean isDone = false;
    public float xPos = 0, yPos = 0;
    private boolean isInit = false;

    public static EntityGoodCar Instance = null;
    private float _sinGen;


    private float speed = 100;
    private Bitmap textureBarrel;

    @Override
    public String GetType() {
        return "EntityGoodCar";
    }

    public static EntityGoodCar Create()
    {
        EntityGoodCar result = new EntityGoodCar();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_BARREL);
        return result;
    }

    public static EntityGoodCar Create(int _layer)
    {
        EntityGoodCar result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    public void SetSpeed(int num )
    {
        speed = num;
    }

    @Override
    public void Init(SurfaceView _view) {
        textureBarrel = ResourceManager.Instance.GetBitmap(R.drawable.electriccar);
        textureBarrel = Bitmap.createScaledBitmap(textureBarrel,
                textureBarrel.getWidth() / 4, textureBarrel.getHeight() / 4 , true);
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.frog);
        Instance = this;
        isInit = true;
        //_gestureDetector = new GestureDetector(_view.getContext(), this);
        //_view.setOnTouchListener(this)
    }
    @Override
    public void Update(float _dt)
    {
        xPos -= speed * _dt;

        if (xPos <= 0){

            xPos = 2000;
        }


        if (yPos + Camera.Instance.GetY() <= 0)
        {

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
        return textureBarrel.getWidth() / 3; }

    @Override
    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "PlayerEntity")
        {

        }
    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }
    @Override
    public boolean IsInit() {
        return false;
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
