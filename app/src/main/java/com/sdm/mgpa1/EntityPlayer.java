package com.sdm.mgpa1;
import android.adservices.appsetid.AppSetId;
import android.app.Activity;
import android.content.Intent;
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
    private int garbageCollected;

    public int getGarbageCollected()
    {
        return garbageCollected;
    }

    private float lerppos = 200;
    private float _movementSpeed = 500;

    // Add variables to keep track of the lerp modification
    private boolean isPowerupLerpActive = false;
    private long powerupLerpStartTime = 0;
    private long powerupLerpDuration = 3000; // Duration in milliseconds (adjust as needed)

    private boolean isPowerupFastActive = false;
    private long powerupFastStartTime = 0;
    private long powerupFastDuration = 3000; // Duration in milliseconds (adjust as needed)
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
        Instance = result;

        return result;
    }

    @Override
    public void Init(SurfaceView _view) {
        isDone = false;
        // Load sprite sheets for idle and moving states
        Bitmap tempIdle = ResourceManager.Instance.GetBitmap(R.drawable.playeridle);
        tempIdle = Bitmap.createScaledBitmap(tempIdle, tempIdle.getWidth() * 2, tempIdle.getHeight() * 2, false);
        spriteSheetIdle = new Sprite(tempIdle, 1, 8, 8);

        Bitmap temp = ResourceManager.Instance.GetBitmap(R.drawable.playerhop);
        temp = Bitmap.createScaledBitmap(temp,temp.getWidth()*2,temp.getHeight()*2,false);
        spriteSheet = new Sprite(temp, 1, 7, 7);

        isInit = true;
        Instance = this;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);

        score = 0;
        lives  = 5;
        SwipeMovement.Instance.onSwipe.subscribe(direction ->
        {
            if(PauseButton.Paused){
                return;
            }
            switch (direction)
            {
                case left:
                    LerpPosition(xPos - lerppos, yPos,  _movementSpeed);
                    AudioManager.Instance.PlayAudio(R.raw.jump, 200);
                    break;
                case right:
                    LerpPosition(xPos + lerppos, yPos, _movementSpeed);
                    AudioManager.Instance.PlayAudio(R.raw.jump, 200);
                    break;
                case up:
                    LerpPosition(xPos, yPos - lerppos, + _movementSpeed);
                    AudioManager.Instance.PlayAudio(R.raw.jump, 200);
                    break;
                case down:
                    LerpPosition(xPos, yPos + lerppos,  + _movementSpeed);
                    AudioManager.Instance.PlayAudio(R.raw.jump, 200);
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
        if (isPowerupLerpActive) {
            // Check if the powerup lerp duration has passed
            long currentTime = System.currentTimeMillis();
            if (currentTime - powerupLerpStartTime >= powerupLerpDuration) {
                // Powerup effect has ended, reset lerppos to 200
                lerppos = 200;
                isPowerupLerpActive = false;
            }
        }

        if (isPowerupFastActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - powerupFastStartTime >= powerupFastDuration) {
                // Powerup effect has ended, reset lerppos to 200
                Log.d("power", "activated");
                _movementSpeed = 500;
                isPowerupFastActive = false;
            }
        }

        if (isMoving) {
            spriteSheet.Update(_dt);
        } else {
            spriteSheetIdle.Update(_dt);
        }

        if (yPos + Camera.Instance.GetY() <= 0 || lives <= 0) // Die Conditions for Player
        {
            Log.d("PLAYER", "HAS DIED");
            AudioManager.Instance.PlayAudio(R.raw.gameover, 1);
            GameOverTextEntity gameOverText  = GameOverTextEntity.Create();
            if (gameOverText != null)
            {
                gameOverText.Init(GamePage.Instance.currentSceneView);
            }
            SetIsDone(true);
            Intent intent = new Intent(GamePage.Instance,Mainmenu.class);
            GamePage.Instance.startActivity(intent);
            //StateManager.Instance.ChangeState("MainGame2");
            Mainmenu.scene = "MainGame2";
            //Init(GamePage.currentSceneView);
            xPos = 500;
            yPos = 500;
            Camera.Instance.SetY(0);
        }


        // Check if player's position exceeds screen boundaries
        float screenWidth = GamePage.Instance.currentSceneView.getWidth();
        // Adjust player position if it exceeds the left or right border
        if (xPos < 10) {
            xPos = 0;
        } else if (xPos > screenWidth - 10) {
            xPos = screenWidth;
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
            AudioManager.Instance.PlayAudio(R.raw.trollsound, 1);
        }
        if (_other.GetType() == "EntityCoin")
        {
            score += 5;
            AudioManager.Instance.PlayAudio(R.raw.addscoresound, 1);
        }
        if (_other.GetType() == "EntityBarrel")
        {
            SwipeMovement.Instance.vibrate(2000, 100);
            AudioManager.Instance.PlayAudio(R.raw.hurtsound, 1);
            Log.d("COLLISION", "OnHit: BARREL");
            lives -= 1;
        }
        if (_other.GetType() == "EntityGoodCar")
        {
            SwipeMovement.Instance.vibrate(2000, 100);

            // Update player position to match car position
            if (_other instanceof EntityGoodCar) {
                EntityGoodCar carEntity = (EntityGoodCar) _other;


                if (carEntity.scoreIncremented == false) {
                    score += 20;

                    carEntity.scoreIncremented = true;
                }
                // Update player position to match car position
                SetPosX(carEntity.GetPosX());
                SetPosY(carEntity.GetPosY() - 75);
            }
        }

        if (_other.GetType() == "EntityBadCar")
        {
            SwipeMovement.Instance.vibrate(2000, 100);

            // Update player position to match car position
            if (_other instanceof EntityBadCar) {


                EntityBadCar carEntity = (EntityBadCar) _other;

                if (carEntity.scoreIncremented == false) {
                    lives -= 1;

                    carEntity.scoreIncremented = true;
                }
                // Update player position to match car position
                SetPosX(carEntity.GetPosX());
                SetPosY(carEntity.GetPosY() - 75);
            }
        }
        if (_other.GetType() == "EntityGarbage")
        {
            garbageCollected += 1;
            AudioManager.Instance.PlayAudio(R.raw.addgarbagescore, 1);
        }
        if (_other.GetType() == "EntityLog")
        {
            // Update player position to match car position
            if (_other instanceof EntityLog) {
                EntityLog log = (EntityLog) _other;

                // Update player position to match car position
                SetPosX(log.GetPosX() - 50 * GamePage.relativeX);
                SetPosY(log.GetPosY() - 50);
            }
        }
        if (_other.GetType() == "MovementPowerUp")
        {
            _movementSpeed = 100;
            isPowerupFastActive = true;
            powerupFastStartTime = System.currentTimeMillis();
            AudioManager.Instance.PlayAudio(R.raw.takespeedpowerup, 1);
        }
        if (_other.GetType() == "Powerup") {
            Log.d("COLLISION", "OnHit: POWERUP ENTITY");

            // Set lerppos to 600 for a while
            lerppos = 600;

            // Activate powerup lerp
            isPowerupLerpActive = true;
            powerupLerpStartTime = System.currentTimeMillis();
            // Perform any other actions related to powerup collision
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
