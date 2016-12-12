package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Track extends GameObject {
    private Point[][] track;

    public Track(Point[][] track) {
        this.track = track;
    }
    
    public static Track loadFromFile(File file) {
        return loadFromFile(file, 0, 0);
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < track[0].length - 1; i++) {
            Point left1 = track[0][i];
            Point left2 = track[0][i+1];
            g2d.drawLine(left1.x, left1.y, left2.x, left2.y);
            Point right1 = track[1][i];
            Point right2 = track[1][i+1];
            g2d.drawLine(right1.x, right1.y, right2.x, right2.y);
        }

        Color c = g2d.getColor();
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i < track[0].length; i++) {
            Point left = track[0][i];
            Point right = track[1][i];
            g2d.drawLine(left.x, left.y, right.x, right.y);
        }
        g2d.setColor(c);
    }
    
    public boolean checkWallCollision(Point pos, int r) {
        for (int i = 1; i < track[0].length; i++) {
            if (Utils.checkCollisionCircle(track[0][i-1], track[0][i], pos, r)) return true;
            if (Utils.checkCollisionCircle(track[1][i-1], track[1][i], pos, r)) return true;
        }
        return false;
    }
    
    public boolean checkCheckpointCollision(Point pos, int r, int currCheckpoint) {
        if (currCheckpoint >= track[0].length-1) return false;
        return Utils.checkCollisionCircle(track[0][currCheckpoint+1], track[1][currCheckpoint+1], pos, r);
    }
    
    public Point getSensorPoint(Point pos, Point end) {
        for (int i = 1; i < track[0].length; i++) {
            Utils.IntersectionInfo info;
            info = Utils.intersect(track[0][i-1], track[0][i], pos, end);
            if (info.intersect) return info.intersectionPoint;
            info = Utils.intersect(track[1][i-1], track[1][i], pos, end);
            if (info.intersect) return info.intersectionPoint;
        }
        return end;
    }

    public static Track loadFromFile(File file, int moveX, int moveY) {
        LinkedList<Point> leftSide = new LinkedList<>();
        LinkedList<Point> rightSide = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int ln = 1;
            String line = reader.readLine();
            while (line != null) {
                String[] sides = line.split(";");
                if (sides.length != 2) {
                    reader.close();
                    throw new IllegalStateException("Expected exactly 2 rows of points. Found: " + sides.length + " in line " + ln);
                }
                String[] left = sides[0].split(",");
                String[] right = sides[1].split(",");
                if (left.length != 2 || right.length != 2) {
                    reader.close();
                    throw new IllegalStateException("Expected exactly 2 values for a point. Found: " + left.length + " and " + right.length + " in line " + ln);
                }
                leftSide.add(new Point(Integer.parseInt(left[0]) + moveX, Integer.parseInt(left[1]) + moveY));
                rightSide.add(new Point(Integer.parseInt(right[0]) + moveX, Integer.parseInt(right[1]) + moveY));
                line = reader.readLine();
                ln++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Point[][] trackPoints = new Point[2][0];
        trackPoints[0] = leftSide.toArray(trackPoints[0]);
        trackPoints[1] = rightSide.toArray(trackPoints[1]);
        return new Track(trackPoints);
    }
}
