package com.sdm.mgpa1;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Entity;

import java.util.Random;

// Created by TanSiewLan2021

public class MainGameSceneState2 implements StateBase  {
    private float timer = 0.0f;
    public static MainGameSceneState2 Instance = null;
    private float _spawnTimer = 0.0f;
    private float _spawnTimerInterval = 5f;
    private SurfaceView _getView;

    @Override
    public String GetName() {
        return "MainGame2";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        _getView = _view;
        // 3. Create Background
        RenderBackground.Create();

        EntityPlayer player = EntityPlayer.Create();
        player.Init(_view);
        player.SetPosX(500);
        player.SetPosY(900);

        int diff = _getView.getWidth()/2;
        Random r = new Random();
        int heightDiff = _getView.getHeight()/2;
        for (int i = 0; i <= 2; ++i)
        {
            int xStart = diff * (i+1);
            int xPos = r.nextInt(xStart);
            int yPos = r.nextInt(heightDiff/2) + heightDiff/2;
            EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
            barrel.Init(_getView);
            barrel.xPos = xPos;
            barrel.yPos = _getView.getHeight() - Camera.Instance.GetY();
        }

//        SmurfEntity _smurf = SmurfEntity.Create();
//        _smurf.Init(_view);
        RenderTextEntity _text = RenderTextEntity.Create();

        _text.Init(_view);

        EntityCoin coin = EntityCoin.Create(LayerConstants.COIN_LAYER);
        coin.Init(_view);
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
        _spawnTimer += _dt;
        if (_spawnTimer >= _spawnTimerInterval)
        {
            _spawnTimer = 0;
            int diff = _getView.getWidth()/3;
            Random r = new Random();
            int heightDiff = _getView.getHeight()/3;
            for (int i = 0; i <= 2; ++i)
            {
                int xStart = diff * (i+1);
                int xPos = r.nextInt(xStart);
                int yPos = r.nextInt(heightDiff/3) + heightDiff/3;
                EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
                barrel.Init(_getView);
                barrel.xPos = xPos;
                barrel.yPos = _getView.getHeight() - Camera.Instance.GetY();
            }
        }
        Camera.Instance.MoveWorldUp(80 * _dt);
        //if (TouchManager.Instance.IsDown()) {
        //6. Example of touch on screen in the main game to trigger back to Main menu
        //StateManager.Instance.ChangeState("Mainmenu");
        //}
    }
}



