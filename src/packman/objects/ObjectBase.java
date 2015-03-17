/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FBody;
import fisica.FBox;
import fisica.FCircle;
import fisica.Fisica;
import org.jbox2d.collision.shapes.ShapeDef;
import org.jbox2d.common.Vec2;
import packman.Event;
import packman.Main;
import processing.core.PGraphics;

/**
 * Represents a game object. Please note that some objects may not extend this,
 * and instead be a standard item, for instance a {@link FBox} or
 * {@link FCircle}
 *
 * The shape is determined by implementing {@link getShapeDef} and
 * {@link getTransformedShapeDef}
 *
 * @author campbell
 */
public abstract class ObjectBase extends FBody {

    private final float width;
    private final float height;
    private final Main main;

    /**
     * Constructs a rectangular body that can be added to a world.
     *
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param main The {@link Main} object
     */
    public ObjectBase(float width, float height, Main main) {
        super();

        this.main = main;
        this.height = Fisica.screenToWorld(height);
        this.width = Fisica.screenToWorld(width);
    }

    @Override
    protected abstract ShapeDef getShapeDef();

    @Override
    protected abstract ShapeDef getTransformedShapeDef();

    public float getHeight() {
        return Fisica.worldToScreen(height);
    }

    public float getWidth() {
        return Fisica.worldToScreen(width);
    }

    public float getUnscaledWidth() {
        return width;
    }

    public float getUnscaledHeight() {
        return height;
    }

    /**
     * Draw this object to the screen
     *
     * @param applet The {@link Main} object, as a PApplet
     */
    @Override
    public void draw(PGraphics applet) {
        preDraw(applet);

//        applet.rotate(PApplet.PI / 4);
        if (m_image != null) {
            drawImage(applet);
        } else {
            applet.rect(0, 0, getWidth(), getHeight());
        }

        postDraw(applet);

        if (shouldDispose(main)) {
            m_world.remove(this);
        }
    }

    @Override
    public void drawDebug(PGraphics applet) {
        preDrawDebug(applet);

        applet.rect(0, 0, getWidth(), getHeight());

        postDrawDebug(applet);
    }

    /**
     * Handle an event
     *
     * @param main The {@link Main} object.
     * @param ev The {@link} to be handled.
     */
    public void handleEvent(Main main, Event ev) {
    }

    public float getFriction() {
        return m_friction;
    }

    public Vec2 getPosition() {
        return m_position;
    }

    public float getRestitution() {
        return m_restitution;
    }

    public boolean shouldDispose(Main main) {
        return isOffScreen(main) && !isStatic();
    }

    public boolean isOffScreen(Main main) {
        float topY = -100;

        if (m_world != null) {
            topY = m_world.getM_topLeftY() + 100;
        }

        return getY() > main.height + 100
                || getY() < topY
                || getX() > main.width + 100
                || getX() < -100;
    }

    public Main getMain() {
        return main;
    }

}
