/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

import java.util.ArrayList;

/**
 *
 * @author campbell
 */
public class ButtonManager {

    private final ArrayList<Button> buttons = new ArrayList<>();

    public ButtonManager() {

    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void draw(int startY, Main main) {
        for (Button button : buttons) {
            button.setHeight(main.height / 14);
            button.autosetWidth(main);
            button.setX((main.width - button.getWidth()) / 2);
            button.setY(startY);
            button.draw(main);
            
            startY += button.getHeight() * 1.25;
        }
    }
}
