/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

/**
 *
 * @author campbell
 */
public class Button {

    private float x, y, width, height;
    private String text;

    public void draw(Main main) {
        if (isInside(main.mouseX, main.mouseY)) {
            main.fill(150, 150, 150);
        } else {
            main.fill(0, 0, 0);
        }
        main.stroke(255, 255, 255);
        
        main.rect(x, y, width, height);
        
        if (text != null) {
            setupTextSize(main);
            main.fill(255, 255, 255);
            main.text(text, getX() + 50, getY() + getHeight() - 5);
        }
    }

    public void setupTextSize(Main main) {
        main.textSize(getHeight());
    }

    public void autosetWidth(Main main) {
        setupTextSize(main);
        setWidth(main.textWidth(text) + 100);
    }

    public boolean isInside(float mx, float my) {
        return mx >= x && my >= y && mx <= x + width && my <= y + height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
