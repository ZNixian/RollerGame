/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FWorld;
import fisica.Fisica;
import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.ShapeDef;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
import packman.Main;
import packman.states.StateInGame;

/**
 *
 * @author campbell
 */
public class ObjectCherry extends ObjectBase {

    private final ObjectPackMann packMann;
    private final StateInGame state;

    public ObjectCherry(Main main, FWorld world,
            float x, float y, ObjectPackMann packMann, StateInGame state) {
        super(32, 32, main);
        this.packMann = packMann;
        this.state = state;
        setPosition(x, y);
        setRestitution(0.3f);
        world.add(this);
        attachImage(main.loadImage("cherry.png"));
    }
    
    @Override
    public void update() {
        super.update();
        if (getTouching().contains(packMann)) {
            m_world.remove(this);
            state.setScore(state.getScore() + 1);
            if (Fisica.parent().random(100) < state.getMode().getChanceForLife()) {
                state.setLives(state.getLives() + 1);
            }
        }
        
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
}
