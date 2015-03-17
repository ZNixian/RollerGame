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

/**
 *
 * @author campbell
 */
public class BoxShape {
    
    private final ObjectBase parent;

    public BoxShape(ObjectBase parent) {
        this.parent = parent;
    }

    public ShapeDef getShapeDef() {
        PolygonDef pd = new PolygonDef();
        pd.setAsBox(parent.getUnscaledWidth() / 2.0f, parent.getUnscaledHeight() / 2.0f);
        pd.density = parent.getDensity();
        pd.friction = parent.getFriction();
        pd.restitution = parent.getRestitution();
        pd.isSensor = parent.isSensor();
        return pd;
    }

    public ShapeDef getTransformedShapeDef() {
        PolygonDef pd = (PolygonDef) getShapeDef();

        XForm xf = new XForm();
        xf.R.set(-parent.getRotation());
        xf.position = Mat22.mul(xf.R, parent.getPosition().negate());

        for (Vec2 vertice : pd.vertices) {
            Vec2 ver = (Vec2) vertice;
            XForm.mulTransToOut(xf, ver, ver);
        }

        return pd;
    }
}
