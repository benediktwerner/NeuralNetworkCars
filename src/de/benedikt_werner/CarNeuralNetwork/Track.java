package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Track {
    private Point[][] track;

    public Track(Point[][] track) {
        this.track = track;
    }
    
    public static Track loadFromFile(File file) {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Point[][] trackPoints = new Point[0][];
        return new Track(trackPoints);
    }
}
