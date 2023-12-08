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

    // New method for sphere-to-rectangle collision
    public static boolean CircleToRectangle(float circleX, float circleY, float radius,
                                            float rectX, float rectY, int rectWidth, int rectHeight)
    {
        // Calculate Distances between Circle and Rectangle
        float xDist = rectX - circleX;
        float yDist = rectY - circleY;
        // abs
        xDist = xDist > 0 ? xDist : -xDist;
        yDist = yDist > 0 ? yDist : -yDist;

        // Check if collided on x and y separately to account for rectangle edges
        boolean xCollided = (radius + rectWidth) > xDist;
        boolean yCollided = (radius + rectHeight) > yDist;

        return xCollided || yCollided;
    }
}
