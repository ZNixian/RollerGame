/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

/**
 * Represents a game mode/difficulty.
 * Each GameMode has a different:<br />
 * * Number of lives PackMann starts with<br />
 * * Chance that a object dropping from the sky is a bomb, not a cherry<br />
 * * Chance that PackMann gets a life when he eats a cherry<br />
 * * How many frames pass between each object drop
 * @author campbell
 */
public enum GameMode {

    VERY_EASY(3, 40, 20, 40),
    EASY(5, 50, 10, 40),
    MEDIUM(5, 70, 7, 30),
    HARD(15, 80, 5, 15);

    private final int startingLives;
    private final int chanceForBomb;
    private final int chanceForLife;
    private final int framesPerDrop;

    private GameMode(int startingLives, int chanceForBomb, int chanceForLife, int framesPerDrop) {
        this.startingLives = startingLives;
        this.chanceForBomb = chanceForBomb;
        this.chanceForLife = chanceForLife;
        this.framesPerDrop = framesPerDrop;
    }

    /**
     * Gets the number of lives PackMann starts with
     * @return The number of lives PackMann starts with
     */
    public int getStartingLives() {
        return startingLives;
    }

    /**
     * Gets the chance that a object dropping from the sky is a bomb, not a cherry<br />
     * Units: %
     * @return Chance that a object dropping from the sky is a bomb, not a cherry
     */
    public int getChanceForBomb() {
        return chanceForBomb;
    }

    /**
     * Gets the chance that PackMann gets a life when he eats a cherry.<br />
     * Units: %
     * @return Chance that PackMann gets a life when he eats a cherry
     */
    public int getChanceForLife() {
        return chanceForLife;
    }

    /**
     * Gets how many frames pass between each object drop
     * @return How many frames pass between each object drop
     */
    public int getFramesPerDrop() {
        return framesPerDrop;
    }
}
