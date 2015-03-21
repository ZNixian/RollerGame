/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FBody;
import fisica.FContact;
import fisica.FWorld;
import org.jbox2d.collision.shapes.ShapeDef;
import packman.BoxShape;
import packman.Main;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 *
 * @author campbell
 */
public class ObjectGrenade extends ObjectBase {

    private int timer;
    private int explodeAnimation;
    private BoxShape shape;

    public static final float MAX_DIST = 128; // was 512
    public static final float MAX_DIST_SQU = PApplet.pow(MAX_DIST, 2);

    public static final float POW = 2048; // was 1024

    public static final int EXPLODE_ANIMATION_DURATION = 40;
    public static final int TIMER_MAX = 100;

    public ObjectGrenade(FWorld world, float x, float y, Main main) {
        super(8, 8, main);
        shape = new BoxShape(this);
        setPosition(x, y);
        setRestitution(0.3f);
        world.add(this);
        timer = TIMER_MAX;
    }

    @Override
    protected ShapeDef getShapeDef() {
        return shape.getShapeDef();
    }

    @Override
    protected ShapeDef getTransformedShapeDef() {
        return shape.getTransformedShapeDef();
    }

    @Override
    public void draw(PGraphics applet) {
        update(); // update everything

        float x = getX(); // cache our X and Y
        float y = getY();

        // If we're in the process of exploding..
        if (explodeAnimation > 1) {
            explodeAnimation++; // increase our timer.

            setVelocity(0, 0); // Reset everything, so we don't move when we're exploding.
            setAngularVelocity(0);
            resetForces();

            // find our current size
            float size = explodeAnimation * 100 / EXPLODE_ANIMATION_DURATION;

            // draw ourselves as an ellipse
            applet.noStroke();
            applet.fill(255, 0, 0, 255 - (explodeAnimation * 255 / EXPLODE_ANIMATION_DURATION));
            applet.ellipse(x, y, size, size);

            // return here, so we don't draw ourselves normally.
            return;
        }

        preDraw(applet); // do translation stuff

        if (timer <= 0) { // if we're armed,
            applet.fill(255, 255, 255); // make ourselves white
        } else {
            // otherwise, fade from black to red.
            applet.fill((TIMER_MAX - timer) * 255 / TIMER_MAX, 0, 0);
        }

        // draw ourselves.
        applet.rect(0, 0, getWidth(), getHeight());

        postDraw(applet); // and un-translate everything.
    }

    @Override
    public void update() {
        super.update();

        // if we've finished our explode animation,
        if (explodeAnimation > EXPLODE_ANIMATION_DURATION) {
            m_world.remove(this); // remove ourself.
            return;
        }

        // a backup, to make sure we do explode, if onContact() doesn't get called.
        if (timer-- < 0 && !getBox2dBody().getBodiesInContact().isEmpty()) {
            onContact(null);
        }
    }

    @Override
    public String getDebugInfoString() {
        return super.getDebugInfoString() + "explodeAnimation: " + explodeAnimation + "\n";
    }

    @Override
    public void onContact(FContact contact) {
        // if:
        //  * we're armed
        //  * we're not already exploding
        if (timer >= 0 || explodeAnimation > 1) {
            return;
        }

        float x = getX(); // cache our position
        float y = getY();
        
        explodeAnimation++; // start the animation ticking
        
        // for every body in the world...
        for (Object body_o : m_world.getBodies()) {
            if (body_o == this) { // we don't want to affect ourselves
                continue;
            }
            
            // cast it to a fbody
            FBody body = (FBody) body_o;
            
            float bx = body.getX(); // cache their X and Y
            float by = body.getY();
            
            // find the distance squared between us and them
            float dist_squ = PApplet.pow(bx - x, 2) + PApplet.pow(by - y, 2);
            
            // if we're i  range
            if (dist_squ < MAX_DIST_SQU) {
                // find the power of our impluse
                float pow = PApplet.cos(dist_squ * PApplet.HALF_PI / MAX_DIST_SQU) * POW;
                
                // find the total X+Y difference
                float difft = Math.abs(bx - x) + Math.abs(by - y);
                
                // find the power of the impluse in each direction.s
                float xpow = (bx - x) * pow / difft;
                float ypow = (by - y) * pow / difft;
                
                // give this body an impulse
                body.addImpulse(xpow, ypow);
            }
        }
    }
}
