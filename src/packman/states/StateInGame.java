/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import fisica.FPoly;
import fisica.FWorld;
import fisica.Fisica;
import packman.Event;
import packman.GameModes;
import packman.Main;
import packman.State;
import packman.events.EventKeys;
import packman.objects.ObjectBase;
import packman.objects.ObjectCherry;
import packman.objects.ObjectGrenade;
import packman.objects.ObjectPackMann;
import static packman.objects.ObjectPackMann.FRICTION;
import packman.objects.ObjectWall;
import processing.core.PApplet;

/**
 * The state that represents in-gameness.
 *
 * @author campbell
 */
public class StateInGame implements State {

    private FWorld world;
    private ObjectPackMann packMann;
    private int lives;
    private int startTimer;
    private int score = 0;

    private int stat_lifeCounter = 0;
    private int stat_lifeChanges = 0;

    private final GameModes mode;

    private static final float baseHeight = 20;

    public StateInGame(GameModes mode) {
        this.mode = mode;
    }

    @Override
    public void setup(Main main, State s) {
        Fisica.init(main);

        setLives(mode.getStartingLives());

        world = new FWorld();
        world.setGravity(0, 300);
        world.setGrabbable(false);

        int width = main.width;
        int height = main.height;

        for (int i = 0; i < 10; i++) {
            float bumpWidth = main.random(50, 250);
            float bumpSide = bumpWidth / 2;
            float bumpHeight = main.random(bumpWidth / 4, bumpWidth / 2);
            float x = main.random(width - bumpSide * 2) + bumpSide;

//            System.out.println("ok: " + x);
            FPoly poly = new FPoly();
            poly.vertex(x - bumpSide, height - baseHeight);
            poly.vertex(x + bumpSide, height - baseHeight);
            poly.vertex(x, height - bumpHeight - baseHeight);
            poly.setStatic(true);
            poly.setFriction(2 - FRICTION);
            world.add(poly);
        }

        ObjectWall floor = new ObjectWall(width / 2, height - baseHeight / 2, width, baseHeight, main);
        floor.setFriction(2 - FRICTION);
        world.add(floor);

        packMann = new ObjectPackMann(world, main);
        packMann.resetPosition(main);
    }

    @Override
    public void draw(Main main) {
        main.background(80, 120, 200);

        if ((main.frameCount % mode.getTicksPerDrop()) == 1
                && packMann.getLastReset() + 1000 < System.currentTimeMillis()) {
            float x = main.random(0, main.width);

            if (main.random(100) > mode.getChanceForBomb()) {
                ObjectCherry c = new ObjectCherry(main, world, x, 0, packMann, this);
            } else {
                ObjectGrenade g = new ObjectGrenade(world, x, 0, main);
            }
        }

        if (packMann.isOffScreen(main)) {
//            System.out.println("ok: " + packMann.getY());
            packMann.resetPosition(main);
            setLives(getLives() - 1);
        }

        if (getLives() < 0) {
            main.popState(1);
            showEndGameScreen(main);
        }

        world.step();
        world.draw();

        main.fill(0, 0, 0);
        main.textSize(20);
        main.text("Lives: " + getLives(), 20, 40);
        main.text("Score: " + score, 20, 60);
        main.text("Mode: " + mode.toString().replace('_', ' ').toLowerCase(), 20, 80);
        main.text("Avg. Lives: " + stat_lifeCounter / stat_lifeChanges, 20, 100);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        for (Object obj : world.getBodies()) {
            if (obj instanceof ObjectBase) {
                ((ObjectBase) obj).handleEvent(main, ev);
            }
        }
        if (ev instanceof EventKeys.EventKeyPressed) {
            EventKeys.EventKeyPressed evkp = (EventKeys.EventKeyPressed) ev;
            if (evkp.key == PApplet.ESC) {
                main.pushState(new StatePause(this));
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        stat_lifeChanges++;
        stat_lifeCounter += lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameModes getMode() {
        return mode;
    }

    public int getStat_lifeCounter() {
        return stat_lifeCounter;
    }

    public int getStat_lifeChanges() {
        return stat_lifeChanges;
    }

    public void showEndGameScreen(Main main) {
        main.pushState(new StateGameOver(getScore(), stat_lifeCounter, stat_lifeChanges, mode));
    }
}
