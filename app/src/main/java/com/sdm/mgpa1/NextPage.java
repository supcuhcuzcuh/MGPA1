package com.sdm.mgpa1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.net.InetSocketAddress;

public class NextPage extends Activity implements OnClickListener, StateBase {
    public static NextPage Instance = null;

    // define button
    private Button btn_nextPage;

    @Override
    protected void onCreate (Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.nextpage);

        btn_nextPage = (Button)findViewById(R.id.btn_quit);
        btn_nextPage.setOnClickListener(this);

        Instance = this;
        StateManager.Instance.Init(new SurfaceView(this));
        GameSystem.Instance.Init(new SurfaceView(this));
        //StateManager.Instance.Start("NextPage");
    }
    @Override
    public String GetName() {
        return "NextPage";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v == btn_nextPage)
        {
            intent.setClass(this, SplashScreen.class);
            StateManager.Instance.ChangeState("MainPage");
        }
        startActivity(intent);
    }
}
