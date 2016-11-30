package de.benedikt_werner.CarNeuralNetwork;

import javax.swing.JFrame;

public class GUI {

    private JFrame frame;
    private Simulation simulation;
    private SimulationPanel simulationPanel;

    public GUI(Simulation simulation) {
        this.simulation = simulation;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 2000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        simulationPanel = new SimulationPanel(simulation);
        frame.getContentPane().add(simulationPanel);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    
    public void repaint() {
        simulationPanel.repaint();
    }
}
