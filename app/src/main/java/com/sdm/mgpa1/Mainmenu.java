package com.sdm.mgpa1;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Mainmenu extends Activity implements OnClickListener, StateBase{

    public static Mainmenu Instance = null;

    //Define button
    private Button btn_start;
    private Button btn_back;
    private Button btn_quit;

    public static String scene = "MainGame";

    @Override
    protected void onCreate (Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mainmenu); // loading our mainmenu.xml that we have created
        //Do not import R. R is a registry that stores information of what is inside mainmenu.xml
        //or any xml file. Even if android studio ask u to do so upon a error.
        //usually this kind of error is due to error in your xml. So pls check any typo in xml.

        btn_start = (Button)findViewById(R.id.btn_start); // get the button and store it
        btn_start.setOnClickListener(this); // Set listener to this button

        btn_back = (Button)findViewById(R.id.btn_back); // get back button and store it in a variable
        btn_back.setOnClickListener(this); // Set listener to this button

        btn_quit = (Button)findViewById(R.id.btn_quit); // get the button and store it
        btn_quit.setOnClickListener(this); // Set listener to this button

        //StateManager.Instance.AddState(new Mainmenu());

        Instance = this; // set the singleton others null error

        //StateManager.Instance.Init(new SurfaceView(this));
        //GameSystem.Instance.Init(new SurfaceView(this));
        //StateManager.Instance.Start("Mainmenu");
        //AudioManager.Instance.PlayAudio(R.raw.gamemusic, 40);
    }

    @Override
    //Invoke CallBack event in the view
    public void onClick(View v)
    {
       Intent intent = new Intent();
       if (v == btn_start)
       {
           intent.setClass(this, GamePage.class);
           StateManager.Instance.ChangeState(scene);
           //StateManager.Instance.ChangeState("MainGame2");
       }
       else if (v == btn_back)
       {
           intent.setClass(this, GamePage.class);
           StateManager.Instance.ChangeState("NextPage");
       }
       else if (v == btn_quit)
       {
            this.finishAffinity();
       }
       startActivity(intent);
    }

    @Override
    protected void onPause(){super.onPause();}

    @Override
    protected void onStop(){super.onStop();}

    @Override
    protected void onDestroy(){super.onDestroy();}

    @Override
    public String GetName() { return "Mainmenu";}

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
}