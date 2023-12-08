package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class EntityCoin implements EntityBase, Collidable {

    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos;
    private  boolean isInit = false;

    public Sprite spriteSheet;

    @Override
    public String GetType() {
        return "EntityCoin";
    }

    public static EntityCoin Create()
    {
        EntityCoin result = new EntityCoin();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
        return result;
    }

    public static EntityCoin Create(int _layer)
    {
        EntityCoin result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public void Init(SurfaceView _view)
    {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.coins);
        bmp = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / 5,bmp.getHeight() / 5,true);
        xPos = 600;
        yPos = 200;
    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, xPos, yPos, null);
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
        return bmp.getWidth() ;
    }

    @Override
    public void OnHit(Collidable _other)
    {
        Log.d("COIN", "COLLIDED WITH PLAYER ENTITY");
        if (_other.GetType() == "PlayerEntity")
        {
            SetIsDone(true);
        }
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
        return 0;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
