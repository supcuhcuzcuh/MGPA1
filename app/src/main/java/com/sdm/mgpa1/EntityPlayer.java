package com.sdm.mgpa1;
import android.adservices.appsetid.AppSetId;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

public class EntityPlayer implements EntityBase, Collidable {
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos;
    private boolean isInit = false;
    private int renderLayer = 1; // layer 1 to be rendered

    public static EntityPlayer Instance = null;
    private boolean hasTouched = false;

    public Sprite spriteSheet = null;
    private int triesCount = 10;
    private Vibrator _vibrator;
    private GestureDetector _gestureDetector;

    @Override
    public String GetType() {
        return "PlayerEntity";
    }

    public static EntityPlayer Create()
    {
        EntityPlayer result = new EntityPlayer();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
        return result;
    }

    public static EntityPlayer Create(int _layer)
    {
        EntityPlayer result = Create();
        result.SetRenderLayer(_layer);

        return result;
    }

    @Override
    public void Init(SurfaceView _view) {
        Bitmap temp = ResourceManager.Instance.GetBitmap(R.drawable.playerhop);
        temp = Bitmap.createScaledBitmap(temp,temp.getWidth()*2,temp.getHeight()*2,true);
        spriteSheet = new Sprite(temp, 1, 7, 7);
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.frog);
        Instance = this;
        isInit = true;
        //_gestureDetector = new GestureDetector(_view.getContext(), this);
        //_view.setOnTouchListener(this);
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);

        SwipeMovement.instance.onSwipe.subscribe(direction ->
        {
            switch (direction)
            {
                case left:
                    LerpPosition(xPos - 200, yPos, 500);
                    break;
                case right:
                    LerpPosition(xPos + 200, yPos, 500);
                    break;
                case up:
                    LerpPosition(xPos, yPos - 200, 500);
                    break;
                case down:
                    LerpPosition(xPos, yPos + 200, 500);
                    break;
            }
        });

    }

    public void LerpPosition(float targetX, float targetY, float duration) {

        long startTime = System.currentTimeMillis();
        float startX = xPos;
        float startY = yPos;

        float progress = 0;

        while (progress < 1) {

            long currentTime = System.currentTimeMillis();
            progress = Math.min(1, (currentTime - startTime) / duration);

            xPos = startX + (targetX - startX) * progress;
            yPos = startY + (targetY - startY) * progress;

            // Render frame
            // Can add a sleep here to control lerp speed

        }

        xPos = targetX;
        yPos = targetY;

    }
    @Override
    public void Update(float _dt) {
        spriteSheet.Update(_dt);

    }

    @Override
    public void Render(Canvas _canvas) {
        // basic rendering with image centered
        //_canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
        spriteSheet.Render(_canvas, (int)xPos, (int)yPos);
    }
    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PLAYER;
    }
    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    public void SetPosX(float x)
    { xPos = x; }

    public void SetPosY(float y)
    { yPos = y; }

    @Override
    public float GetRadius() {
        return 0;
    }

    public void startVibrate()
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150, 10));
        }
        else
        {
            long pattern[] = {0, 50, 0};
            _vibrator.vibrate(pattern, -1);
        }
    }
    @Override
    public void OnHit(Collidable _other) {
        Log.d("COLLISION", "Player Hit");
        if (_other.GetType() == "SmurfEntity") // another entity
        {
            Log.d("COLLISION", "OnHit: SMURF ENTITY");
            SetIsDone(true);
            AudioManager.Instance.PlayAudio(R.raw.trollsound, 200);
        }
        if (_other.GetType() == "EntityBarrel")
        {
            Log.d("COLLISION", "OnHit: BARREL");
            //SetIsDone(true);
        }
    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        renderLayer = _newLayer;
    }
}
