/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FBody;
import fisica.FBox;
import fisica.FCircle;
import fisica.FContact;
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
        return Fisica.worldToScreen(height); // scale the height from JBox units to pixels
    }

    public float getWidth() {
        return Fisica.worldToScreen(width); // scale the height from JBox units to pixels
    }

    public float getUnscaledWidth() {
        return width;
    }

    public float getUnscaledHeight() {
        return height;
    }

    /**
     * Draw this object to the screen
     * Do not add behavour here - see {@link update()} 
     * @param applet The {@link Main} object, as a PApplet
     */
    @Override
    public void draw(PGraphics applet) {
        update(); // update the object. Subclasses can use this to add behavour
        // without affecting drawing.

        preDraw(applet); // setup the translation and rotation.

        if (m_image != null) { // if we have an image, draw it. Otherwise, draw a rectangle.
            drawImage(applet);
        } else {
            applet.rect(0, 0, getWidth(), getHeight());
        }

        postDraw(applet);
    }

    /**
     * Do all behavour-related things, such as removing when we leave the screen.
     */
    public void update() {
        if (shouldDispose(main)) { // if we should be disposed...
            if (Main.DEBUG) { // just add a little debug info,
                System.out.println("Removing "
                        + getClass().getSimpleName() + " at X=" + getX() + " Y=" + getY());
            }
            m_world.remove(this); // before removing ourselves fro the world.
        }
    }

    /**
     * Draw debug data
     * @param applet The {@link PGraphics} to draw to.
     */
    @Override
    public void drawDebug(PGraphics applet) {
        preDrawDebug(applet);

        applet.rect(0, 0, getWidth(), getHeight()); // just draw ourselves as a rectangle.

        postDrawDebug(applet);
    }

    /**
     * Handle an event.
     *
     * @param main The {@link Main} object.
     * @param ev The {@link} to be handled.
     */
    public void handleEvent(Main main, Event ev) {
        // let other people override this, if they need to.
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

    /**
     * Should this object be removed?
     * @param main The {@link Main} object.
     * @return {@code true} if this object should be removed, {@code false} otherwise.
     */
    public boolean shouldDispose(Main main) {
        return isOffScreen(main) && !isStatic();
    }

    /**
     * Is this object off the screen?
     * @param main The {@link Main} object.
     * @return {@code true} if this object is off the screen, {@code false} otherwise.
     */
    public boolean isOffScreen(Main main) {
        float topY = -100; // Y at which objects should be removed. We set it,
        // in case m_world is null.

        if (m_world != null) {
            topY = m_world.getM_topLeftY() + 100; // but if it isn't null,
            // set it from here, but with 100px padding.
        }

        // cache the width and height.
        float pxHeight = getHeight();
        float pxWidth = getWidth();

        return getY() > main.height + pxHeight
                || getY() < topY
                || getX() > main.width + pxWidth
                || getX() < -pxWidth;
    }

    /**
     * Get our stored {@link Main} object.
     * @return The stored {@link Main} object.
     */
    public Main getMain() {
        return main;
    }

    /**
     * Called when this object touches another
     * @param contact The contact that has been formed.
     */
    public void onContact(FContact contact) {
    }
}
