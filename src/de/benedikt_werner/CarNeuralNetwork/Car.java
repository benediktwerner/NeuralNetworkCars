package de.benedikt_werner.CarNeuralNetwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public class Car extends GameObject {
    
    private static final int[] SENSOR_ANGLES = {-90, -45, 0, 45, 90};
    private static final int SENSOR_RANGE = 50, RADIUS = 20;
    
    private NeuralNetwork nn;
    private double speed;
    private double turnRate;
    private double angle;
    private Point pos;
    private Track track;
    private int checkpointCount;
    
    private Point[] sensorData;
    
    public Car(Track track) {
        this.track = track;
        speed = 1;
        turnRate = 1;
        angle = 0;
        pos = new Point(300, 300);
        checkpointCount = 0;
        sensorData = new Point[SENSOR_ANGLES.length];
        for (int i = 0; i < sensorData.length; i++)
            sensorData[i] = new Point();
    }
    
    public void update() {
        if (track.checkCheckpointCollision(pos, RADIUS, checkpointCount)) checkpointCount++;
        if (track.checkWallCollision(pos, RADIUS)) die();
        updateSensorData();
        // TODO: get command from nn

        angle += turnRate;
        angle %= 360;
        int deltaX = (int) Math.round(Math.sin(Math.toRadians(angle)) * speed);
        int deltaY = (int) Math.round(Math.cos(Math.toRadians(angle)) * speed);
        pos.x = pos.x + deltaX;
        pos.y = pos.y + deltaY;
    }
    
    private void updateSensorData() {
        for (int i = 0; i < sensorData.length; i++) {
            double a = Math.toRadians(angle + SENSOR_ANGLES[i]);
            Point end = new Point(pos.x + (int) Math.round(Math.sin(a) * SENSOR_RANGE), pos.y + (int) Math.round(Math.cos(a) * SENSOR_RANGE));
            sensorData[i] = track.getSensorPoint(pos, end);
        }
    }

    public void draw(Graphics2D g2d) {
        // Car
        fillCircle(g2d, pos, 20);
        
        // Sensors
        Stroke s = g2d.getStroke();
        Color c = g2d.getColor();
        g2d.setStroke(new BasicStroke(2));
        
        for (Point p : sensorData) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(pos.x, pos.y, p.x, p.y);
            g2d.setColor(Color.RED);
            fillCircle(g2d, p, 7);
        }
        g2d.setStroke(s);
        g2d.setColor(c);
    }
    
    private void die() {
        //TODO: compute fitness and remove from updates
        System.out.println("Car died!");
    }
    
    private static void fillCircle(Graphics2D g2d, Point p, int width) {
        g2d.fillOval(p.x - width/2, p.y - width/2, width, width);
    }
}
