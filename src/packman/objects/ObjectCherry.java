/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import fisica.FWorld;
import fisica.Fisica;
import org.jbox2d.collision.shapes.ShapeDef;
import packman.BoxShape;
import packman.Main;
import packman.states.StateInGame;

/**
 * Represents a cherry.
 *
 * @author campbell
 */
public class ObjectCherry extends ObjectBase {

    private final ObjectPackMann packMann;
    private final StateInGame state;
    private final BoxShape shape;

    public ObjectCherry(Main main, FWorld world,
            float x, float y, ObjectPackMann packMann, StateInGame state) {

        super(32, 32, main);
        this.packMann = packMann;
        this.state = state;

        shape = new BoxShape(this);

        setPosition(x, y);
        setRestitution(0.3f);
        world.add(this);
        attachImage(main.loadImage("cherry.png"));
    }

    @Override
    public void update() {
        super.update();
        // check if we're touching PackMann, and get eaten by him if so.
        if (getTouching().contains(packMann)) {
            m_world.remove(this); // remove ourself.
            state.setScore(state.getScore() + 1); // increast the score.

            // Check to see if PackMann gets an extra life.
            if (Fisica.parent().random(100) < state.getMode().getChanceForLife()) {
                state.setLives(state.getLives() + 1);
            }
        }

    }

    @Override
    protected ShapeDef getShapeDef() {
        return shape.getShapeDef();
    }

    @Override
    protected ShapeDef getTransformedShapeDef() {
        return shape.getTransformedShapeDef();
    }
}
