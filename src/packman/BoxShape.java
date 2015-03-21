/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.ShapeDef;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
import packman.objects.ObjectBase;
import packman.objects.ObjectCherry;

/**
 * A helper class to make it easier to implement box-shaped {@link ObjectBase}s<br />
 * For an example, see {@link ObjectCherry}
 *
 * @author campbell
 */
public class BoxShape {

    private final ObjectBase parent; // object that owns us

    public BoxShape(ObjectBase parent) {
        this.parent = parent;
    }

    /**
     * For the parent object, returns what it should return for
     * {@code getShapeDef} if it is a box.
     *
     * @return
     */
    public ShapeDef getShapeDef() {
        PolygonDef pd = new PolygonDef();
        pd.setAsBox(parent.getUnscaledWidth() / 2.0f,
                parent.getUnscaledHeight() / 2.0f);
        // set our new polygondef to
        // be a box, like it's parent

        // now, we can copy across all the attributes.
        pd.density = parent.getDensity();
        pd.friction = parent.getFriction();
        pd.restitution = parent.getRestitution();
        pd.isSensor = parent.isSensor();
        
        return pd;
    }

    /**
     * For the parent object, returns what it should return for
     * {@code getTransformedShapeDef} if it is a box.
     *
     * @return
     */
    public ShapeDef getTransformedShapeDef() {
        PolygonDef pd = (PolygonDef) getShapeDef(); // we base off of the normal box

        XForm xf = new XForm(); // a new form, as this holds position and
        // rotation data
        
        // copy across the position and rotation
        xf.R.set(-parent.getRotation());
        xf.position = Mat22.mul(xf.R, parent.getPosition().negate());

        for (Vec2 vertice : pd.vertices) {
            Vec2 ver = (Vec2) vertice;
            XForm.mulTransToOut(xf, ver, ver);
        }

        return pd;
    }
}
