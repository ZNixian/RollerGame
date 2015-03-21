/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

/**
 * Represents a button on screen.
 *
 * @author campbell
 */
public class Button {

    /**
     * The position and size of out button. Width can be auto-set with
     * {@link Button.autosetWidth}
     */
    private float x, y, width, height;

    /**
     * The text to display on this button.
     */
    private String text;

    /**
     * Draw this button to the screen.
     *
     * @param main The {@link Main} object to draw to.
     */
    public void draw(Main main) {
        if (isInside(main.mouseX, main.mouseY)) { // if the mouse is hovering, change colour
            main.fill(150, 150, 150); // set the fill. This has an effect when we draw a button.
        } else {
            main.fill(0, 0, 0);
        }

        main.stroke(255, 255, 255);

        main.rect(x, y, width, height); // draw the button to the screen.

        if (text != null) { // if we have text,
            setupTextSize(main); // set the font size, depending on our height,
            main.fill(255, 255, 255); // set the fill and
            main.text(text, getX() + 50, getY() + getHeight() - 5); // draw the text.
        }
    }

    /**
     * Sets the current text size to our height.
     *
     * @param main The {@link Main} object to set the height on.
     */
    public void setupTextSize(Main main) {
        main.textSize(getHeight());
    }

    /**
     * Automatically set the width, so that the text fits with 50px of padding
     * on either side.
     *
     * @param main The {@link Main} object.
     */
    public void autosetWidth(Main main) {
        setupTextSize(main); // we need this, otherwise we might use someone else's font size.
        setWidth(main.textWidth(text) + 100); // 100px of padding.
    }

    /**
     * Is a position (hint: mouse) inside of this button?
     *
     * @param mx The X position to check
     * @param my The Y position to check
     * @return {@code true} if the given position is inside the box,
     * {@code false} otherwise
     */
    public boolean isInside(float mx, float my) {
        return mx >= x
                && my >= y
                && mx <= x + width
                && my <= y + height;
    }

    /**
     * Get the button's X position
     *
     * @return the X position.
     * @see getY()
     * @see setX(float)
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the button's X position
     *
     * @param x The new X position
     * @see getX()
     * @see setY(float)
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get the button's Y position
     *
     * @return the Y position.
     * @see getX()
     * @see setY(float)
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the button's Y position
     *
     * @param y The new Y position
     * @see getY()
     * @see setX(float)
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets this button's width.
     *
     * @return the button's width
     * @see getHeight()
     * @see setWidth(float)
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets this button's width
     *
     * @param width The new width value.
     * @see getWidth()
     * @see setHeight(float)
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Gets this button's height.
     *
     * @return the button's height
     * @see getWidth()
     * @see setHeight(float)
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets this button's height
     *
     * @param height The new height value.
     * @see getHeight()
     * @see setWidth(float)
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Gets the text displayed on this button. Can be {@code null} if there is
     * no text
     *
     * @return the button's label
     * @see setText(String)
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the text displayed on this button. Can be {@code null} for no label.
     *
     * @param text The new text. {@code null} for no text
     * @see getText()
     */
    public void setText(String text) {
        this.text = text;
    }
}
