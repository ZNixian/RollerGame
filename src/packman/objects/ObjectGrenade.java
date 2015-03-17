/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FBody;
import fisica.FWorld;
import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.ShapeDef;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
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

    public static final float MAX_DIST = 128; // was 512
    public static final float MAX_DIST_SQU = PApplet.pow(MAX_DIST, 2);

    public static final float POW = 2048; // was 1024

    public static final int EXPLODE_ANIMATION_DURATION = 40;
    public static final int TIMER_MAX = 100;

    public ObjectGrenade(FWorld world, float x, float y, Main main) {
        super(8, 8, main);
        setPosition(x, y);
        setRestitution(0.3f);
        world.add(this);
        timer = TIMER_MAX;
    }

    @Override
    protected ShapeDef getShapeDef() {
        PolygonDef pd = new PolygonDef();
        pd.setAsBox(getUnscaledWidth() / 2.0f, getUnscaledHeight() / 2.0f);
        pd.density = m_density;
        pd.friction = m_friction;
        pd.restitution = m_restitution;
        pd.isSensor = m_sensor;
        return pd;
    }

    @Override
    protected ShapeDef getTransformedShapeDef() {
        PolygonDef pd = (PolygonDef) getShapeDef();

        XForm xf = new XForm();
        xf.R.set(-m_angle);
        xf.position = Mat22.mul(xf.R, m_position.negate());

        for (Vec2 vertice : pd.vertices) {
            Vec2 ver = (Vec2) vertice;
            XForm.mulTransToOut(xf, ver, ver);
        }

        return pd;
    }

    @Override
    public void draw(PGraphics applet) {
        float x = getX();
        float y = getY();
        if (explodeAnimation > EXPLODE_ANIMATION_DURATION) {
//            removeFromWorld();
            m_world.remove(this);
//            System.out.println("ok!");
            return;
        }
        if (explodeAnimation > 1) {
            explodeAnimation++;
            setVelocity(0, 0);
            setAngularVelocity(0);
            resetForces();
            float size = explodeAnimation * 100 / EXPLODE_ANIMATION_DURATION;
            applet.noStroke();
            applet.fill(255, 0, 0, 255 - (explodeAnimation * 255 / EXPLODE_ANIMATION_DURATION));
            applet.ellipse(x, y, size, size);
            return;
        }
        preDraw(applet);
        if (timer <= 0) {
            applet.fill(255, 255, 255);
        } else {
            applet.fill((TIMER_MAX - timer) * 255 / TIMER_MAX, 0, 0);
        }
        applet.rect(0, 0, getWidth(), getHeight());
        postDraw(applet);
        if (timer-- < 0 && !getTouching().isEmpty()) {
            explodeAnimation++;
            for (Object body_o : m_world.getBodies()) {
                if (body_o == this) {
                    continue;
                }
                FBody body = (FBody) body_o;
                float bx = body.getX();
                float by = body.getY();
                float dist_squ = PApplet.pow(bx - x, 2) + PApplet.pow(by - y, 2);
                if (dist_squ < MAX_DIST_SQU) {
                    float pow = PApplet.cos(dist_squ * PApplet.HALF_PI / MAX_DIST_SQU) * POW;
                    float difft = Math.abs(bx - x) + Math.abs(by - y);
                    float xpow = (bx - x) * pow / difft;
                    float ypow = (by - y) * pow / difft;
                    body.addImpulse(xpow, ypow);
                }
            }
        }
    }

    @Override
    public String getDebugInfoString() {
        return super.getDebugInfoString() + "explodeAnimation: " + explodeAnimation + "\n";
    }
}
