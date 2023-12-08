package com.sdm.mgpa1;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Entity;

import java.util.Random;

// Created by TanSiewLan2021

public class MainGameSceneState2 implements StateBase  {
    private float timer = 0.0f;
    public static MainGameSceneState2 Instance = null;

    Random randomgen;


    @Override
    public String GetName() {
        return "MainGame2";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {

        // 3. Create Background
        RenderBackground.Create();

        EntityPlayer player = EntityPlayer.Create();
        player.Init(_view);
        player.SetPosX(_view.getWidth()/2);
        player.SetPosY(_view.getHeight()/2);

        EntityGoodCar moving = EntityGoodCar.Create();
        moving.xPos = _view.getWidth()/2 + 400;
        moving.yPos = _view.getHeight()/2;
        moving.Init(_view);

        int diff = _view.getWidth()/3;
        Random r = new Random();
        int heightDiff = _view.getHeight()/2;
        for (int i = 0; i < 3; ++i)
        {
            int xStart = diff * (i+1);
            int xPos = r.nextInt(xStart);
            int yPos = r.nextInt(heightDiff/2) + heightDiff/2;
            EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
            barrel.Init(_view);
            barrel.xPos = xPos;
            barrel.yPos = yPos;
        }

//        SmurfEntity _smurf = SmurfEntity.Create();
//        _smurf.Init(_view);
        RenderTextEntity _text = RenderTextEntity.Create();
        _text.Init(_view);



        // AudioManager.Instance.PlayAudio(R.raw.gamemusic, 40);
        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        // 4. Clear any instance instantiated via EntityManager.
        EntityManager.Instance.Clean();
        // 5. Clear or end any instance instantiated via GamePage.
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        EntityManager.Instance.Update(_dt);


    }
}



