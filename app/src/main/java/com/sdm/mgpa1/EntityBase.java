package com.sdm.mgpa1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public interface EntityBase
{

    //used for entities such as background
    enum ENTITY_TYPE{
        //ENT_PLAYER,
        //ENT_PAUSE,
        //ENT_NEXT,

        ENT_TEXT,
        ENT_SMURF,
        ENT_DEFAULT,
    }

    boolean IsDone();
    void SetIsDone(boolean _isDone);
    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);
    boolean IsInit();
    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);
	ENTITY_TYPE GetEntityType();
}
