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
                    xPos -= 200;
                    break;
                case right:
                    xPos += 200;
                    break;
                case up:
                    yPos -= 200;
                    break;
                case down:
                    yPos +=200;
                    break;
            }
        });

    }
    @Override
    public void Update(float _dt) {
        spriteSheet.Update(_dt);
//        if (TouchManager.Instance.HasTouch()) {
//            // check collision here
//            float imgRadius = spriteSheet.GetWidth() * 0.5f;
//            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
//                    TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched)
//            {
//                // collided
//                hasTouched = true;
//                xPos = TouchManager.Instance.GetPosX();
//                yPos = TouchManager.Instance.GetPosY();
//            }
//        }
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
