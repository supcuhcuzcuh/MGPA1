package com.sdm.mgpa1;
import android.adservices.appsetid.AppSetId;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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

    private boolean hasTouched = false;

    public static EntityPlayer Instance = null;

    // Add a boolean to track the player's movement status
    private boolean isMoving = false;
    public Sprite spriteSheet = null;

    public Sprite spriteSheetIdle = null;
    private int triesCount = 10;
    private Vibrator _vibrator;
    private GestureDetector _gestureDetector;
    private int levelMultiplier = 20;
    private int score;
    private int lives;
    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
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

        // Load sprite sheets for idle and moving states
        Bitmap tempIdle = ResourceManager.Instance.GetBitmap(R.drawable.playeridle);
        tempIdle = Bitmap.createScaledBitmap(tempIdle, tempIdle.getWidth() * 2, tempIdle.getHeight() * 2, true);
        spriteSheetIdle = new Sprite(tempIdle, 1, 8, 8);

        Bitmap temp = ResourceManager.Instance.GetBitmap(R.drawable.playerhop);
        temp = Bitmap.createScaledBitmap(temp,temp.getWidth()*2,temp.getHeight()*2,true);
        spriteSheet = new Sprite(temp, 1, 7, 7);

        isInit = true;
        Instance = this;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);

        score = 0;
        lives  = 5;
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
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        Log.d("METRICS", Integer.toString(metrics.widthPixels));
    }

    public void LerpPosition(float targetX, float targetY, float duration) {

        isMoving = true; // Set the movement status to true

        long startTime = System.currentTimeMillis();
        float startX = xPos;
        float startY = yPos;

        float progress = 0;

        while (progress < 1) {

            long currentTime = System.currentTimeMillis();
            progress = Math.min(2, (currentTime - startTime) / duration);

            xPos = startX + (targetX - startX) * progress;
            yPos = startY + (targetY - startY) * progress;

            // Render frame
            // Can add a sleep here to control lerp speed

        }
        xPos = targetX;
        yPos = targetY;

        isMoving = false; // Reset the movement status to false when done moving
    }

    @Override
    public void Update(float _dt) {
        if (isMoving) {
            spriteSheet.Update(_dt);
        } else {
            spriteSheetIdle.Update(_dt);
        }

        if (yPos + Camera.Instance.GetY() <= 0)
        {
            Log.d("PLAYER", "HAS DIED");
            GameOverTextEntity gameOverText  = GameOverTextEntity.Create();
            if (gameOverText != null)
            {
                gameOverText.Init(GamePage.Instance.currentSceneView);
            }
            SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        // basic rendering with image centered
        //_canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
        if (isMoving) {
            spriteSheet.Render(_canvas, (int) xPos * GamePage.relativeX, (int) (yPos + Camera.Instance.GetY()) * GamePage.relativeY);
        } else {
            spriteSheetIdle.Render(_canvas, (int) xPos * GamePage.relativeX, (int) (yPos + Camera.Instance.GetY()) * GamePage.relativeY);
        }
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
            lives -= 1;
        }
        if (_other.GetType() == "EntityCoin")
        {
            score += 20;
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
