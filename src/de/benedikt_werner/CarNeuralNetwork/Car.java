package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Graphics2D;
import java.awt.Point;

public class Car extends GameObject {
    private NeuralNetwork nn;
    private double speed;
    private double turnRate;
    private double angle;
    private Point position;
    
    public Car(){
        speed = 5;
        turnRate = 0.2;
        angle = 0;
        position = new Point(300, 300);
    }
    
    public void update() {
        // TODO: get command from nn

        angle += turnRate;
        int deltaX = (int) (Math.cos(angle) * speed);
        int deltaY = (int) (Math.sin(angle) * speed);
        position.x = position.x + deltaX;
        position.y = position.y + deltaY;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawOval(position.x, position.y, 10, 10);
    }
}
