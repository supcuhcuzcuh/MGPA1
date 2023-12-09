package com.sdm.mgpa1;

// Created by TanSiewLan2021

public class Collision
{
    public static boolean SphereToSphere(float x1, float y1, float radius1, float x2, float y2, float radius2)
    {
        float xVec = x2 - x1;
        float yVec = y2 - y1;

        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;

        return true;
    }


    public static boolean CircleToRectangle(float circleX, float circleY, float radius,
                                            float rectX, float rectY, int rectWidth, int rectHeight)
    {
        // Calculate Distances between Circle and Rectangle
        float xDist = Math.abs(rectX - circleX);
        float yDist = Math.abs(rectY - circleY);

        // Check if collided on x and y separately to account for rectangle edges
        boolean xCollided = xDist < (rectWidth / 2 + radius);
        boolean yCollided = yDist < (rectHeight / 2 + radius);

        return xCollided && yCollided;
    }

}
