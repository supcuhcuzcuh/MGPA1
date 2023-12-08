package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.SurfaceView;

public class EntityBarrel implements EntityBase, Collidable{
    private Bitmap _bitmap = null;

    private boolean isDone = false;
    public float xPos = 0, yPos = 0;
    private boolean isInit = false;

    public static EntityBarrel Instance = null;

    private Bitmap textureBarrel;

    @Override
    public String GetType() {
        return "EntityBarrel";
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
                textureBarrel.getWidth() / 2, textureBarrel.getHeight() / 2, true);
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.frog);
        Instance = this;
        isInit = true;
        //_gestureDetector = new GestureDetector(_view.getContext(), this);
        //_view.setOnTouchListener(this)
    }
    @Override
    public void Update(float _dt)
    {

    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(textureBarrel, xPos, yPos,null);
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
        return textureBarrel.getWidth() / 3f;    }

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
