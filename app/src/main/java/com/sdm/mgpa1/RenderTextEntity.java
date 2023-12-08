package com.sdm.mgpa1;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class RenderTextEntity implements EntityBase {

    // Paint Object
    Paint paint = new Paint();

    // Variable to be used to set colors
    private int red = 0, green = 0, blue = 0; // Colors range from 0 to 255

    // We want to use our own font type,
    protected Typeface myfont;

    // We want to render FPS as text on the screen
    // variables that we want to use
    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps = 0;

    private boolean isDone = false;
    private boolean isInit = false;

    EntityPlayer player = EntityPlayer.Instance;
    int score = player.getScore();
    int lives = player.lives;
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // Load font
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "LatoBlack.ttf");

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        // set fps
        long currentTime = System.currentTimeMillis();
        long lastTime = currentTime;
        if (currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }
        frameCount++;

        score = player.getScore();
        lives = player.getLives();
    }

    @Override
    public void Render(Canvas _canvas) {

        // Set font color and size
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);

        // Draw FPS
        paint.setTypeface(myfont);
        _canvas.drawText("FPS: " + fps, 20, 40, paint);

        // Draw any other text
        _canvas.drawText("Score: " + score, 20, 80, paint);
        _canvas.drawText("Lives: " + lives, 20, 120, paint);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.TEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static RenderTextEntity Create()
    {
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_TEXT;}
}
