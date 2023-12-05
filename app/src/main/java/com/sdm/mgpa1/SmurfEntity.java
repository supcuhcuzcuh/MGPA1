package com.sdm.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.view.SurfaceView;
import java.util.Random;

import kotlin.random.URandomKt;

public class SmurfEntity implements EntityBase, Collidable{
    
	 // 1. Declare the use of spritesheet using Sprite class.
    public Bitmap bmp = null;
    public Sprite spritesheet = null;
    private boolean isDone = false;
    private boolean isInit = false;

	 // Variables to be used or can be used.
    public float xPos, yPos, xDir, yDir, lifeTime;
    
	 // For use with the TouchManager.class
    private boolean hasTouched = false;

    int ScreenWidth, ScreenHeight;

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
        // New method using our own resource manager : Returns pre-loaded one if exists
        // 2. Loading spritesheet
        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurfsprite), 4, 4, 16);
	
        // 3. Get some random position of x and y
        Random ranGen = new Random(); // Random generator under the java utility library
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        isInit = true;

    }

    @Override
    public void Update(float _dt) {
        
        // 4. Update spritesheet
        spritesheet.Update(_dt);
        // 5. Deal with the touch on screen for interaction of the image using collision check
        if (TouchManager.Instance.HasTouch())
        {
            float imgRadius = spritesheet.GetWidth() * 0.5f; // finding the radius of the smurf image
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched)
            {
                // Collided!
                hasTouched = true;
                // 7. Drag the sprite around the screen
                xPos = TouchManager.Instance.GetPosX(); // dragging will change the x and y pos
                yPos = TouchManager.Instance.GetPosY();

                xPos += xDir * _dt;
                yPos += yDir * _dt;
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
       
        // This is for our sprite animation!
        spritesheet.Render(_canvas, (int)xPos, (int)yPos);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.SMURF_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static SmurfEntity Create()
    {
        SmurfEntity result = new SmurfEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_SMURF;}

    @Override
    public String GetType() {
        return "SmurfEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return spritesheet.GetHeight() * 0.5f;
    }

    @Override
    // check collision between 2 entities
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "StarEntity") //Another Entity
        {
            SetIsDone(true);
            //Play an audio
        }
    }
}
