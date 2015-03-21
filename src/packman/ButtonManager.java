/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

import java.util.ArrayList;

/**
 * Easily manage a vertical list of buttons, as used in the menus.
 * @author campbell
 */
public class ButtonManager {

    /**
     * The {@link Button}s to be displayed.
     */
    private final ArrayList<Button> buttons = new ArrayList<>();

    public ButtonManager() {

    }

    /**
     * Get all the drawable {@link Button}s
     * @return 
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Draw all the buttons, centered horizontally.
     * @param startY The Y poxition to start the list of buttons.
     * @param main The {@link Main} object to draw all the buttons to.
     */
    public void draw(int startY, Main main) {
        for (Button button : buttons) {
            // Set the button's height to a 1/14th of the window's height.
            button.setHeight(main.height / 14);
            
            // autoset the width of the button to match the width of the text.
            button.autosetWidth(main);
            
            // set the X value, so the button is centered.
            button.setX((main.width - button.getWidth()) / 2);
            
            // set the Y position.
            button.setY(startY);
            
            // draw it to the screen
            button.draw(main);
            
            // Increase the start Y, so the buttons nicely stack.
            startY += button.getHeight() * 1.25;
        }
    }
}
