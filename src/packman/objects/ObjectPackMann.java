/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FWorld;
import fisica.Fisica;
import org.jbox2d.collision.shapes.CircleDef;
import org.jbox2d.collision.shapes.ShapeDef;
import packman.Main;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * A PackMann object.
 *
 * @author campbell
 */
public class ObjectPackMann extends ObjectBase {

    /**
     * PackMann's size.
     */
    private static final int SIZE = 32;

    /**
     * The amount of rotational force applyed to PackMann when he is moved. No
     * idea what the units are, but increasing this makes him spin up faster.
     * Note that this doesn't change his maximum speed.
     *
     * @see ObjectPackMann#MAX_SPEED
     */
    private static final int ACCELERATION = 45; // was 45

    /**
     * The amount of friction PackMann experiances against surfaces.
     */
    public static final float FRICTION = 0.25f;

    /**
     * PackMann's maximum speed, in RPM.
     */
    public static final int MAX_SPEED = 600; // was 600 RPM

    /**
     * PackMann's sprites.
     */
    private final PImage[][] sprites = new PImage[2][2];

    private float lastRotVelocity; // packmann's rotational velocity.
    private int animationStage = 0;// packmann's stage through his animation.
    
    private long lastReset; // when packmann was last reset

    /**
     * Makes a new PackMann instance
     *
     * @param world The {@link FWorld} packmann is to be placed in
     * @param main The {@link Main} object
     */
    public ObjectPackMann(FWorld world, Main main) {
        super(SIZE, SIZE, main);
        setFriction(FRICTION);
        setDamping(0);
        setAngularDamping(PApplet.PI / 16);
        setRestitution(0.5f);
        setAllowSleeping(false);
        world.add(this);
//        getBox2dBody().m_flags |= e_fixedRotationFlag;
        registerIcons(main);
        attachImage(sprites[0][0]);
    }

    @Override
    public void update() {
        super.update(); // update everything
        
        Main main = (Main) Fisica.parent(); // cache our parents

        // if a key is pressed, then apply the appropriate torque
        if (main.keyPressed) {
            if (main.keyCode == PApplet.RIGHT) {
                addTorque(ACCELERATION);
            }
            if (main.keyCode == PApplet.LEFT) {
                addTorque(-ACCELERATION);
            }
        }

        // cache our angular velocity
        float dir = getAngularVelocity();

        // find the Max speed in Radians per Second
        float MAX_SPEED_RPS = (MAX_SPEED * PApplet.TWO_PI / 60);

        // if we're spinning too fast, then slow oruselves down.
        if (Math.abs(dir) > MAX_SPEED_RPS) {
            if (dir > 0) {
                dir = MAX_SPEED_RPS;
            } else {
                dir = -MAX_SPEED_RPS;
            }
            setAngularVelocity(dir);
        }

        // save our last velocity, in case we stop moving.
        if (dir == 0) {
            dir = lastRotVelocity;
        } else {
            lastRotVelocity = dir;
        }
        
        // find the animation index for diraction
        int animDir = dir > 0 ? 0 : 1;
        
        // play the animation.
        if (main.frameCount % 20 == 0) {
            animationStage++;
            animationStage %= sprites[0].length;

            if (dir > PApplet.QUARTER_PI) {
                animationStage = 1;
            }
        }
        
        attachImage(sprites[animDir][animationStage]);
    }
    
    public void resetPosition(Main main) {
        setPosition(main.width / 2, -32);
        resetForces();
        setVelocity(0, 0);
        setAngularVelocity(0);
        setRotation(0);
        
        lastReset = System.currentTimeMillis();
    }

    @Override
    protected ShapeDef getShapeDef() {
        CircleDef pd = new CircleDef();
        pd.radius = Fisica.screenToWorld(SIZE) / 2.0f;
        pd.density = m_density;
        pd.friction = m_friction;
        pd.restitution = m_restitution;
        pd.isSensor = m_sensor;
        return pd;
    }

    @Override
    protected ShapeDef getTransformedShapeDef() {
        CircleDef pd = (CircleDef) getShapeDef();
        pd.localPosition.set(m_position);
        return pd;
    }

    private void registerIcons(Main main) {
        for (int dir = 0; dir < 2; dir++) {
            for (int state = 0; state < 2; state++) {
                sprites[dir][state] = main.loadImage("packmann_" + (dir == 0 ? "r" : "l") + "_" + state + ".png");
            }
        }
    }

    public long getLastReset() {
        return lastReset;
    }

    @Override
    public boolean shouldDispose(Main main) {
        // don't actually destroy ourselves - see StateInGame
        return false;
    }

}
