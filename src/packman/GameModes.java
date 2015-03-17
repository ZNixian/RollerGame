/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

/**
 *
 * @author campbell
 */
public enum GameModes {

    VERY_EASY(3, 40, 20, 40),
    EASY(5, 50, 10, 40),
    MEDIUM(5, 70, 7, 30),
    HARD(15, 80, 5, 15);

    private final int startingLives;
    private final int chanceForBomb;
    private final int chanceForLife;
    private final int ticksPerDrop;

    private GameModes(int startingLives, int chanceForBomb, int chanceForLife, int ticksPerDrop) {
        this.startingLives = startingLives;
        this.chanceForBomb = chanceForBomb;
        this.chanceForLife = chanceForLife;
        this.ticksPerDrop = ticksPerDrop;
    }

    public int getStartingLives() {
        return startingLives;
    }

    public int getChanceForBomb() {
        return chanceForBomb;
    }

    public int getChanceForLife() {
        return chanceForLife;
    }

    public int getTicksPerDrop() {
        return ticksPerDrop;
    }
}
