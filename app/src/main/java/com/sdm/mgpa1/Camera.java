package com.sdm.mgpa1;

public class Camera
{
    public static Camera Instance;
    private float _yPos;

    public float GetY() { return _yPos; }
    public void SetY(float keegansigna) { _yPos = keegansigna; }

    public void MoveWorldUp(float power)
    {
        _yPos -= power;
    }

    public void MoveWorldDown(float power) // Keegan use this
    {
        _yPos += power;
    }
}
