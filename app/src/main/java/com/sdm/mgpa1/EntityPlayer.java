package com.sdm.mgpa1;
import android.adservices.appsetid.AppSetId;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.view.SurfaceView;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class EntityPlayer implements EntityBase, Collidable {
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos;
    private boolean isInit = false;
    private int renderLayer = 1; // layer 1 to be rendered

    public static EntityPlayer Instance = null;
    private boolean hasTouched = false;

    private int triesCount = 10;
    private Vibrator _vibrator;

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
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_SMURF;
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
        return 0f;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.worm);
        Instance = this;
        isInit = true;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    @Override
    public void Update(float _dt) {
        if (TouchManager.Instance.HasTouch())
        {
            // check collision here
            float imgRadius = bmp.getWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                    TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched)
            {
                // collided
                hasTouched = true;
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        // basic rendering with image centered
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
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
        if (_other.GetType() == "NextEntity") // another entity
        {
            SetIsDone(true);
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
