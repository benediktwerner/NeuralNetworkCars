package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Graphics2D;
import java.awt.Point;

public class Car extends GameObject {
    
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
        speed = 5;
        turnRate = 0.2;
        angle = 0;
        pos = new Point(300, 300);
        checkpointCount = 0;
    }
    
    public void update() {
        updateSensorData();
        // TODO: get command from nn

        angle += turnRate;
        int deltaX = (int) (Math.cos(angle) * speed);
        int deltaY = (int) (Math.sin(angle) * speed);
        pos.x = pos.x + deltaX;
        pos.y = pos.y + deltaY;
    }
    
    private void updateSensorData() {
        sensorData = new Point[] {
                new Point(pos.x - 30, pos.y),
                new Point(pos.x - 15, pos.y + 15),
                new Point(pos.x,      pos.y + 30),
                new Point(pos.x + 15, pos.y + 15),
                new Point(pos.x + 30, pos.y)
        };
    }

    public void draw(Graphics2D g2d) {
        // Car
        fillCircle(g2d, pos, 20);
        
        // Sensors
        for (Point p : sensorData) {
            g2d.drawLine(pos.x, pos.y, p.x, p.y);
            fillCircle(g2d, p, 5);
        }
    }
    
    private static void fillCircle(Graphics2D g2d, Point p, int width) {
        g2d.fillOval(p.x - width/2, p.y - width/2, width, width);
    }
}
