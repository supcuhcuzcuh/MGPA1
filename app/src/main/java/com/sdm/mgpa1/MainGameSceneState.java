package com.sdm.mgpa1;

import android.graphics.Canvas;
import android.view.SurfaceView;

import androidx.constraintlayout.helper.widget.Layer;

import java.util.Random;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase  {
    private float timer = 0.0f;
    public static MainGameSceneState Instance = null;
    private float _spawnTimer = 0.0f;
    private float _spawnTimerInterval = 5f;
    public SurfaceView getView;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        Instance = this;
        getView = _view;
        // 3. Create Background 
        RenderBackground.Create();

        EntityPlayer player = EntityPlayer.Create();
        player.Init(_view);
        player.SetPosX(500);
        player.SetPosY(500);

        int diff = 1000/3;
        int heightDiff = getView.getHeight()/2;

        Random rBarrel = new Random();
        Random rGarbage = new Random();
        //Random rLog = new Random();

        for (int i = 0; i <= 2; ++i)
        {
            int xStart = diff * (i+1);
            int xPos = rBarrel.nextInt(xStart);
            //int yPos = r.nextInt(heightDiff/2) + heightDiff/2;
            EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
            int speedtemp = rBarrel.nextInt(100);
            speedtemp = speedtemp + 40;

            barrel.SetSpeed(speedtemp);
            barrel.Init(getView);
            barrel.xPos = xPos;
            barrel.yPos = getView.getHeight() - Camera.Instance.GetY();
        }

        for (int i = 0; i <= 1; ++i)
        {
            int xStart = diff * (i+1);
            int xPos = rGarbage.nextInt(xStart);
            //int yPos = rGarbage.nextInt(heightDiff/2) + heightDiff/2;
            EntityGarbage garbage = EntityGarbage.Create(LayerConstants.GARBAGE_LAYER);
            garbage.Init(getView);
            garbage.xPos = xPos;
            garbage.yPos = getView.getHeight() - Camera.Instance.GetY();
        }

//        for (int i = 0; i < 1; ++i)
//        {
//            int xStart = diff * (i+1);
//            int xPos = rLog.nextInt(xStart);
//            //int yPos = rLog.nextInt(heightDiff/2) + heightDiff/2;
//            EntityLog log = EntityLog.Create(LayerConstants.LOG_LAYER);
//            log.Init(getView);
//            log.xPos = xPos;
//            log.yPos = getView.getHeight() - Camera.Instance.GetY();
//        }

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
    public void Update(float _dt) {
        EntityManager.Instance.Update(_dt);
        _spawnTimer += _dt;

        if (_spawnTimer >= _spawnTimerInterval) {
            _spawnTimer = 0;
            int diff = 1000 / 3;

            // Random Chance Power up Spawn
            if (new Random().nextInt(3) == 0) {
                spawnPowerUp();
            }

            Random rBarrel = new Random();
            Random rGarbage = new Random();
            Random rCoins = new Random();

            int heightDiff = getView.getHeight() / 3;

            for (int i = 0; i <= 1; ++i) {
                int xStart = diff * (i + 1);
                int xPos = rBarrel.nextInt(xStart) + (diff * 1);
                int yPos = rBarrel.nextInt(heightDiff / 3) + heightDiff / 3;
                EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
                int speedtemp = rBarrel.nextInt(100);
                speedtemp = speedtemp + 40;

                barrel.SetSpeed(speedtemp);
                barrel.Init(getView);
                barrel.xPos = xPos;
                barrel.yPos = getView.getHeight() - Camera.Instance.GetY();
            }

            for (int i = 0; i <= 1; ++i) {
                int xStart = diff * (i + 2);
                int xPos = rGarbage.nextInt(xStart) + (diff * 1);
                int yPos = rGarbage.nextInt(heightDiff / 2) + heightDiff / 2;
                EntityGarbage garbage = EntityGarbage.Create(LayerConstants.GARBAGE_LAYER);
                garbage.Init(getView);
                garbage.xPos = xPos;
                garbage.yPos = 1000 - Camera.Instance.GetY();
            }

            for (int i = 0; i <= 1; ++i) {
                int xStart = diff * (i + 3);
                int xPos = rCoins.nextInt(xStart) + (diff * 1);
                int yPos = rCoins.nextInt(heightDiff / 2) + heightDiff / 2;
                EntityCoin coins = EntityCoin.Create(LayerConstants.COIN_LAYER);
                coins.Init(getView);
                coins.xPos = xPos;
                coins.yPos = 1000 - Camera.Instance.GetY();
            }


//            Random rLog = new Random();
//            for (int i = 0; i <= 1; ++i)
//            {
//                int xStart = diff * (i+3);
//                int xPos = rLog.nextInt(xStart);
//                //int yPos = rLog.nextInt(heightDiff/2) + heightDiff/2;
//                EntityLog log = EntityLog.Create(LayerConstants.LOG_LAYER);
//                log.Init(getView);
//                log.xPos = xPos;
//                log.yPos = 1000 - Camera.Instance.GetY();
//            }
        }
        Camera.Instance.MoveWorldUp(80 * _dt);
        //if (TouchManager.Instance.IsDown()) {
            //6. Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        //}
    }

    private void spawnPowerUp() {
        int diff = getView.getWidth() / 3;
        Random r = new Random();
        int heightDiff = getView.getHeight() / 3;

        EntitySpeedPowerUp powerUp = EntitySpeedPowerUp.Create(LayerConstants.SPEEDPOWERUP_LAYER);
        powerUp.Init(getView);

        int xPos = r.nextInt(diff);
        int yPos = r.nextInt(heightDiff / 3) + heightDiff / 3;

        powerUp.xPos = xPos;
        powerUp.yPos = 1000 - Camera.Instance.GetY();
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
}



