package com.sdm.mgpa1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.sdm.mgpa1.EntityBase;

public class PauseButton implements EntityBase {
    public float x, y; // Position of the button
    public float width, height; // Dimensions of the button
    private Bitmap PauseImage;

    public static  boolean Paused;





    @Override
    public boolean IsDone() {
        return false;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        // Implement if needed
    }

    @Override
    public void Init(SurfaceView _view) {
        // Initialization if needed
        PauseImage = BitmapFactory.decodeResource(_view.getResources(),R.drawable.pause);
        PauseImage = Bitmap.createScaledBitmap(PauseImage,
                PauseImage.getWidth() / 6, PauseImage.getHeight() / 6 , true);

        SwipeMovement.Instance.onTapDown.subscribe(v2 -> {

            if(withinMouseTouch(v2)) {

                Log.d("Gang", "Init:GRRRR BAh ");
                Paused = !Paused;
            }
        });

    }

    @Override
    public void Update(float _dt) {
        // Update logic if needed
    }

    @Override
    public void Render(Canvas _canvas) {
        // Draw the button on the canvas
        _canvas.drawBitmap(PauseImage,x,y,null);
    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return 0;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        // Implement if needed
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType() {
        return null; // Adjust based on your entity types
    }

    // Add a method to check if the button is clicked
    public boolean withinMouseTouch(V2 pos)
    {
        V2 scale = new V2(PauseImage.getWidth(),PauseImage.getHeight());
        boolean xDiff = (pos.x > x && pos.x < x + scale.x);
        boolean yDiff = (pos.y > y && pos.y < y + scale.y);
        return xDiff && yDiff;
    }

    // Add a method to handle button click

}