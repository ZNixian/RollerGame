/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import fisica.FContact;
import fisica.FContactListener;
import fisica.FContactResult;
import fisica.FPoly;
import fisica.FWorld;
import packman.Event;
import packman.GameMode;
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

    /**
     * The physics world
     */
    private FWorld world;
    
    /**
     * The {@link ObjectPackMann} object.
     */
    private ObjectPackMann packMann;
    
    /**
     * The number of lives PackMann has/
     */
    private int lives;
    
    /**
     * The number of Cherries PackMann has eaten.
     */
    private int score = 0;

    
    /**
     * The running sum of PackMann's lives.
     * PackMann's life counter is added to this
     * whenever he loses or gains lives.
     */
    private int stat_lifeCounter = 0;
    
    /**
     * The total number of changes to PackMann's lives.
     */
    private int stat_lifeChanges = 0;

    /**
     * The current {@link GameMode}
     */
    private final GameMode mode;

    /**
     * How heigh the floor is.
     */
    private static final float baseHeight = 20;

    public StateInGame(GameMode mode) {
        this.mode = mode;
    }

    @Override
    public void setup(Main main, State s) {
        setLives(mode.getStartingLives()); // set up PackMann's starting lives

        world = new FWorld(); // make the physics world
        world.setGravity(0, 300); // set the gravity
        world.setGrabbable(false); // make it so that people cannot drag things around
        
        // add a contact listener. This tells objects when they touch something else.
        world.setContactListener(new FContactListener() {

            @Override
            public void contactStarted(FContact contact) {
                // if it is possible to tell body1 they are in a collision
                if(contact.getBody1() instanceof ObjectBase) {
                    // tell them
                    ((ObjectBase) contact.getBody1()).onContact(contact);
                }
                
                // same as above, but for body2
                if(contact.getBody2() instanceof ObjectBase) {
                    ((ObjectBase) contact.getBody2()).onContact(contact);
                }
            }

            @Override
            public void contactPersisted(FContact contact) {
            }

            @Override
            public void contactEnded(FContact contact) {
            }

            @Override
            public void contactResult(FContactResult result) {
            }
        });

        int width = main.width; // cache width and height
        int height = main.height;

        // make 10 random bumps
        for (int i = 0; i < 10; i++) {
            // make a random bump width
            float bumpWidth = main.random(50, 250);
            
            // find the width of one side of the bump
            float bumpSide = bumpWidth / 2;
            
            // find the bump's height. This is between 1/4 and 1/2 of the bump's width
            float bumpHeight = main.random(bumpWidth / 4, bumpWidth / 2);
            
            // find the X position of the bump
            float x = main.random(width - bumpSide * 2) + bumpSide;

            // make the bump as a polygon
            FPoly poly = new FPoly();
            
            // add all 3 points
            poly.vertex(x - bumpSide, height - baseHeight); // left-most point
            poly.vertex(x + bumpSide, height - baseHeight); // right-most pojnt
            poly.vertex(x, height - bumpHeight - baseHeight); // top point
            
            // set it up, and add it to the world
            poly.setStatic(true); // bumps don't roll around
            poly.setFriction(2 - FRICTION); // nice and grippy
            world.add(poly); // add it to the world
        }

        // make the floor
        ObjectWall floor = new ObjectWall(width / 2, height - baseHeight / 2, width, baseHeight, main);
        floor.setFriction(2 - FRICTION); // make it grippy
        world.add(floor); // add it to the world

        // create the PackMann
        packMann = new ObjectPackMann(world, main);
        packMann.resetPosition(main);
    }

    @Override
    public void draw(Main main) {
        // nice, blue background
        main.background(80, 120, 200);

        // if:
        //  * it has been at least 1 second sicne packmann was last reset
        //      (died or the game started)
        //  * it has been an amount of time specified by the current mode
        // then:
        //  * add a new cherry/bomb
        if ((main.frameCount % mode.getFramesPerDrop()) == 1
                && packMann.getLastReset() + 1000 < System.currentTimeMillis()) {
            
            // come up with a random x position
            float x = main.random(0, main.width);

            // we come up with a random number
            // between 1 and 100. If it is more than
            // the % chance that this gamemode has for
            // a bomb drop, then drop a cherry.
            if (main.random(100) > mode.getChanceForBomb()) {
                ObjectCherry c = new ObjectCherry(main, world, x, 0, packMann, this);
                // cherrys and grenades add themselves to the world, we don't have to do it.
            } else {
                ObjectGrenade g = new ObjectGrenade(world, x, 0, main);
            }
        }

        // if PackMann is off-screen, then reset him
        if (packMann.isOffScreen(main)) {
            packMann.resetPosition(main); // reset him
            setLives(getLives() - 1); // deduct 1 life
        }

        // if he had 1 life, and died, then end the game
        // and show the gameover screen.
        if (getLives() < 0) {
            main.popState(1);
            showEndGameScreen(main);
        }

        world.step(); // simulate the world
        world.draw(); // draw the world
        
        // if debug mode is on, draw everything with debug info.
        if (Main.DEBUG) {
            world.drawDebug();
        }

        main.fill(0, 0, 0); // text is black
        main.textSize(20); // and 20px
        
        // print all the stats that people are likely to care about.
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
        // if we get an event, send it to all the objects in our world
        for (Object obj : world.getBodies()) {
            if (obj instanceof ObjectBase) {
                ((ObjectBase) obj).handleEvent(main, ev);
            }
        }
        
        // if ESC was pressed, show the pause screen.
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

    public GameMode getMode() {
        return mode;
    }

    public int getStat_lifeCounter() {
        return stat_lifeCounter;
    }

    public int getStat_lifeChanges() {
        return stat_lifeChanges;
    }

    /**
     * Show the game-over screen.
     * Note: you still have to popState(1) yourself
     * @param main The {@link Main} object
     */
    public void showEndGameScreen(Main main) {
        main.pushState(new StateGameOver(getScore(), stat_lifeCounter, stat_lifeChanges, mode));
    }
}
