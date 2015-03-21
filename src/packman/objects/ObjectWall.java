/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.objects;

import org.jbox2d.collision.shapes.ShapeDef;
import packman.BoxShape;
import packman.Main;
import static packman.objects.ObjectPackMann.FRICTION;

/**
 * A simple wall object.
 * Used to represent the floor.
 * @author campbell
 */
public class ObjectWall extends ObjectBase {

    private final BoxShape shape = new BoxShape(this);

    public ObjectWall(float x, float y, float width, float height, Main main) {
        super(width, height, main);
        setPosition(x, y);
        setFriction(2 - FRICTION);
        setStatic(true);
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
