package de.benedikt_werner.CarNeuralNetwork;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Simulation {
    public List<GameObject> gameObjects = new LinkedList<>();
    private boolean isRunning = true;
    private int framePause = 10;
    private GUI gui = null;
    private Track track = null;

    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation();
        Track track = Track.loadFromFile(new File("C:\\dev\\workspace\\NeuralNetworkCars\\track.txt"), 500, 500);
        simulation.addGameObject(new Car(track));
        simulation.setTrack(track);

        GUI gui = new GUI(simulation);
        gui.setVisible(true);
        simulation.setGui(gui);

        while (simulation.isRunning) {
            simulation.update();
            Thread.sleep(simulation.framePause);
        }
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go) {
        gameObjects.remove(go);
    }

    private void update() {
        if (gui == null) return;

        for (GameObject go : gameObjects) {
            go.update();
        }
        gui.repaint();
    }

    public void setFramePause(int pause) {
        framePause = pause;
    }

    public void setGui(GUI newGUI) {
        gui = newGUI;
    }
    
    public void setTrack(Track newTrack) {
        if (track != null) removeGameObject(track);
        track = newTrack;
        addGameObject(track);
    }
}
