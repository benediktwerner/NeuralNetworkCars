package de.benedikt_werner.CarNeuralNetwork;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
    private static final long serialVersionUID = -4562684701500546351L;
    private Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        super();
        this.simulation = simulation;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (GameObject go : simulation.gameObjects)
            go.draw(g2d);
    }
}
