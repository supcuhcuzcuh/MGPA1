package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.SurfaceView;

public class EntityGoodCar implements EntityBase, Collidable{
    private Bitmap _bitmap = null;

    private boolean isDone = false;
    public float xPos = 0, yPos = 0;
    private boolean isInit = false;

    public static EntityGoodCar Instance = null;

    private Bitmap textureBarrel;

    @Override
    public String GetType() {
        return "EntityBarrel";
    }

    public static EntityGoodCar Create()
    {
        EntityGoodCar result = new EntityGoodCar();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.GoodCar);
        return result;
    }

    public static EntityGoodCar Create(int _layer)
    {
        EntityGoodCar result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public void Init(SurfaceView _view) {
        textureBarrel = ResourceManager.Instance.GetBitmap(R.drawable.car2);
        textureBarrel = Bitmap.createScaledBitmap(textureBarrel,
                textureBarrel.getWidth() , textureBarrel.getHeight() , true);
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.frog);
        Instance = this;
        isInit = true;
        //_gestureDetector = new GestureDetector(_view.getContext(), this);
        //_view.setOnTouchListener(this)
    }
    @Override
    public void Update(float _dt)
    {

        if(Collision.CircleToRectangle(EntityPlayer.Instance.GetPosX(), EntityPlayer.Instance.GetPosY(),EntityPlayer.Instance.GetRadius(), xPos,yPos,_bitmap.getWidth(),_bitmap.getHeight()))
        {



        }
    }

    @Override
    public void Render(Canvas _canvas) {
        float centerX = xPos - textureBarrel.getWidth() / 2f;
        float centerY = yPos - textureBarrel.getHeight() / 2f;
        _canvas.drawBitmap(textureBarrel, centerX, centerY, null);
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
        return textureBarrel.getWidth() ;    }

    @Override
    public void OnHit(Collidable _other) {

    }

    @Override
    public boolean IsDone() {
        return false;
    }

    @Override
    public void SetIsDone(boolean _isDone) {

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
