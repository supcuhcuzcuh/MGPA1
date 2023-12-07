package com.sdm.mgpa1;

import android.graphics.Canvas;
import android.view.SurfaceView;

public class EntityCoin implements EntityBase, Collidable {
    @Override
    public String GetType() {
        return "EntityCoin";
    }
    @Override
    public void Init(SurfaceView _view)
    {

    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public float GetPosX() {
        return 0;
    }

    @Override
    public float GetPosY() {
        return 0;
    }

    @Override
    public float GetRadius() {
        return 0;
    }

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
