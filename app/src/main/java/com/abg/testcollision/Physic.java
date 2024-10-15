package com.abg.testcollision;

import com.abg.testcollision.entity.GameObject;

public class Physic {
    public static boolean checkCollision(GameObject one, GameObject two) {
        return overlaps(one.x, one.width, two.x, two.width) && overlaps(one.y, one.height, two.y, two.height);
    }

    static boolean overlaps(double point1, double length1, double point2, double length2) {
        double highestStartPoint = Math.max(point1, point2);
        double lowestEndPoint = Math.min(point1 + length1, point2 + length2);
        return highestStartPoint < lowestEndPoint;
    }
}
