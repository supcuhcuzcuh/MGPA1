package com.sdm.mgpa1;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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

    private PauseButton button;

    private  NextLevelButton nextscenebutton;


    private  int score;

    @Override
    public String GetName() {
        return "MainGame2";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // 3. Create Background
        RenderBackground2.Create();



        EntityPlayer player = EntityPlayer.Create(LayerConstants.SMURF_LAYER);
        player.Init(GamePage.currentSceneView);
        player.SetPosX(500);
        player.SetPosY(500);

        int diff = GamePage.currentSceneView.getWidth()/2;
        Random r = new Random();
        int heightDiff = GamePage.currentSceneView.getHeight()/2;
        for (int i = 0; i <= 1; ++i)
        {
            int xStart = diff * (i+1);
            int xPos = r.nextInt(xStart);
            int yPos = r.nextInt(heightDiff/2) + heightDiff/2;
            EntityBarrel barrel = EntityBarrel.Create(LayerConstants.BARREL_LAYER);
            barrel.Init(GamePage.currentSceneView);
            barrel.xPos = xPos;
            barrel.yPos = GamePage.currentSceneView.getHeight() - Camera.Instance.GetY();
        }

        score = player.getScore();
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

    private void spawnPowerUp() {
        int diff = GamePage.currentSceneView.getWidth() / 3;
        Random r = new Random();
        int heightDiff = GamePage.currentSceneView.getHeight() / 3;

        JumpDistancePowerup powerUp = JumpDistancePowerup.Create(LayerConstants.BARREL_LAYER);
        powerUp.Init(GamePage.currentSceneView);

        int xPos = r.nextInt(diff);
        int yPos = r.nextInt(heightDiff / 3) + heightDiff / 3;

        powerUp.xPos = xPos;
        powerUp.yPos = -Camera.Instance.GetY();
    }
    float bad_fix_timer = 0f;
    boolean bad_fix_boolean = true;
    @Override
    public void Update(float _dt) {

        if (bad_fix_timer < 1)
        {
            EntityPlayer.Instance.SetPosX(500);
            EntityPlayer.Instance.SetPosY(500);
            Camera.Instance.SetY(0);
            bad_fix_timer += _dt;
        }
        else
        {
            if (bad_fix_boolean)
            {
                EntityPlayer.Instance.Init(GamePage.currentSceneView);
                bad_fix_boolean = false;
                button = new PauseButton();
                button.Init(GamePage.currentSceneView);
                button.x = 50;
                button.y = 50;
                EntityManager.Instance.AddEntity(button, EntityBase.ENTITY_TYPE.PButton);
                button.Init(GamePage.currentSceneView);
            }
        }
        if (button != null)
            button.Update(_dt);




        if(PauseButton.Paused){

            return;
        }



        EntityManager.Instance.Update(_dt);
        _spawnTimer += _dt;
        if (_spawnTimer >= _spawnTimerInterval)
        {
            _spawnTimer = 0;
            // 1 in 3 chance to spawn a power-up
            if (new Random().nextInt(3) == 0) {
                spawnPowerUp();
            }

            int diff = GamePage.currentSceneView.getWidth()/3;
            Random r = new Random();
            int heightDiff = GamePage.currentSceneView.getHeight()/3 ;


            for (int i = 0; i <= 0; ++i)
            {
                int xStart = diff * (i+1);
                int xPos = r.nextInt(xStart);
                int yPos = r.nextInt(heightDiff/3) + heightDiff/3;


                int x2Start = diff * (i+1);
                int x2Pos = r.nextInt(x2Start);


                EntityGoodCar barrel = EntityGoodCar.Create(LayerConstants.BARREL_LAYER);

                EntityBadCar badcar = EntityBadCar.Create(LayerConstants.BARREL_LAYER);

                int speedtemp2 = r.nextInt(200);
                speedtemp2 = speedtemp2 + 90;

                int speedtemp = r.nextInt(100);
                speedtemp = speedtemp + 40;


                badcar.SetSpeed(speedtemp2);
                badcar.Init(GamePage.currentSceneView);
                badcar.xPos = x2Pos;
                badcar.yPos = -Camera.Instance.GetY();


                barrel.SetSpeed(speedtemp);
                barrel.Init(GamePage.currentSceneView);
                barrel.xPos = xPos;
                barrel.yPos = -Camera.Instance.GetY();
            }
        }
        Camera.Instance.MoveWorldDown(80 * _dt);
        //if (TouchManager.Instance.IsDown()) {
        //6. Example of touch on screen in the main game to trigger back to Main menu
        //StateManager.Instance.ChangeState("Mainmenu");
        //}
    }
}



