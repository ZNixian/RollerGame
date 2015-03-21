/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Button;
import packman.ButtonManager;
import packman.Event;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 * The pause menu.
 * @author campbell
 */
public class StatePause implements State {

    // make things easy to draw
    private final ButtonManager buttons = new ButtonManager();
    
    private final Button continueB = new Button(); // continue the game
    private final Button quitB = new Button(); // end the game
    
    private final StateInGame parent;

    public StatePause(StateInGame parent) {
        this.parent = parent;
    }

    @Override
    public void setup(Main main, State s) {
        // set the buttons up
        
        continueB.setText("CONTINUE");
        buttons.getButtons().add(continueB);
        quitB.setText("END GAME");
        buttons.getButtons().add(quitB);
    }

    @Override
    public void draw(Main main) {
        // black with white text
        main.background(0, 0, 0);
        main.fill(255, 255, 255);
        
        // text is 1/6 of the screen height
        main.textSize(main.height / 6);
        
        // the text
        String s = "GAME\nPAUSED";
        
        // draw it
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2 - (main.height / 6));

        // draw the buttons
        buttons.draw(main.height / 2 + (main.height / 6), main);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        // if the mouse was clicked
        if (ev instanceof EventMouse.EventMouseClicked) {
            // take appropriate action for the clicked button.
            if (continueB.isInside(main.mouseX, main.mouseY)) {
                // pop ourselves away, so the active state
                // is the in-game state
                main.popState(1);
            } else if (quitB.isInside(main.mouseX, main.mouseY)) {
                // pop ourselves and the in-game state away,
                // so we are back to the menu
                main.popState(2);
                
                // then push on the game over screen.
                parent.showEndGameScreen(main);
            }
        }
    }

}
