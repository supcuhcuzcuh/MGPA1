package com.sdm.mgpa1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2023

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {

        // 2. We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        // Please add state, NextPage.
        StateManager.Instance.AddState(new NextPage());
        // Plese add state, MainGameSceneState.
        StateManager.Instance.AddState(new MainGameSceneState());
        
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

}
