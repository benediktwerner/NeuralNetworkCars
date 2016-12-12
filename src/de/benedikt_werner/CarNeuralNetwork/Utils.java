package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Point;

public class Utils {
    
    public static class IntersectionInfo {
        public Point intersectionPoint;
        public boolean intersect;
        public IntersectionInfo() {
            intersect = false;
        }
        public IntersectionInfo(Point p) {
            intersect = true;
            intersectionPoint = p;
        }
    }
    
    public static IntersectionInfo intersect(Point a1, Point b1, Point a2, Point b2) {
        Point r = fromTo(a1, b1);
        Point s = fromTo(a2, b2);
        if (cross(r, s) == 0)
            return new IntersectionInfo();
        else {
            double t = cross(sub(a2,a1), s) / (double) cross(r,s);
            double u = cross(sub(a2,a1), r) / (double) cross(r,s);
            if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                int x = (int) Math.round(a1.x + t*r.x);
                int y = (int) Math.round(a1.y + t*r.y);
                return new IntersectionInfo(new Point(x, y));
            }
            else return new IntersectionInfo();
        }
    }
    
    /**
     * Check collision of a point with a circle.
     * @param p Point to check
     * @param c Center of the circle
     * @param radius Radius of the circle
     * @return true if the point is within the circle, false otherwise
     */
    public static boolean checkCollisionPointCircle(Point p, Point c, int radius) {
        int d = (p.x - c.x) * (p.x - c.x) + (p.y - c.y) * (p.y - c.y);
        return d <= (radius*radius);
    }
    
    /**
     * Check collision of a box with the bounding box of a circle.
     * @param a Corner point of the box
     * @param b Corner point of the box
     * @param c Center of the circle
     * @param radius Radius of the circle
     * @return true if the line collides with the box, false otherwise
     */
    public static boolean checkCollisionCircleBoundingBox(Point a, Point b, Point c, int radius) {
        Point minAB = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
        Point maxAB = new Point(Math.max(a.x, b.x), Math.max(a.y, b.y));
        Point minC = new Point(c.x - radius, c.y - radius);
        Point maxC = new Point(c.x + radius, c.y + radius);
        
        return !(maxAB.x < minC.x || minAB.x > maxC.x ||
                 maxAB.y < minC.y || minAB.y > maxC.y);
    }
    
    /**
     * Check if a line collides with a circle.
     * @param a Start of the line
     * @param b End of the line
     * @param c Center of the circle
     * @param radius Radius of the circle
     * @return true if the line collides with the circle, false otherwise
     */
    public static boolean checkCollisionCircle(Point a, Point b, Point c, int radius) {
        if (!checkCollisionCircleBoundingBox(a, b, c, radius))
            return false;
        
        Point u = fromTo(a, b);
        Point am = fromTo(a, c);
        
        int norm = Math.abs(u.x * am.y - u.y * am.x);
        double denom = Math.sqrt(u.x * u.x + u.y * u.y);
        double ci = norm / denom;
        return ci < radius;
    }
    
    /**
     * Check if a line segment collides with a circle.
     * @param a Start of the line segment
     * @param b End of the line segment
     * @param c Center of the circle
     * @param radius Radius of the circle
     * @return true if the line segment collides with the circle, false otherwise
     */
    public static boolean checkCollisionSegmentCircle(Point a, Point b, Point c, int radius) {
        if (!checkCollisionCircle(a, b, c, radius))
            return false;
        
        Point ab = fromTo(a, b);
        Point am = fromTo(a, c);
        Point bm = fromTo(b, c);
        
        int pscal1 = ab.x * am.x + ab.y * am.y;
        int pscal2 = (-ab.x) * bm.x + (-ab.y) * bm.y;
        
        if (pscal1 >= 0 && pscal2 >= 0)
            return true;
        
        if (checkCollisionPointCircle(a, c, radius) || checkCollisionPointCircle(b, c, radius))
            return true;
        return false;
    }
    
    private static int cross(Point a, Point b) {
        return a.x * b.y - a.y * b.x;
    }
    
    private static Point sub(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }
    
    private static Point fromTo(Point a, Point b) {
        return new Point(b.x - a.x, b.y - a.y);
    }
}
