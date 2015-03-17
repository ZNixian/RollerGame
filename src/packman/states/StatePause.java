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
 *
 * @author campbell
 */
public class StatePause implements State {

    private final ButtonManager buttons = new ButtonManager();
    private final Button continueB = new Button();
    private final Button quitB = new Button();
    private final StateInGame parent;

    public StatePause(StateInGame parent) {
        this.parent = parent;
    }

    @Override
    public void setup(Main main, State s) {
        continueB.setText("CONTINUE");
        buttons.getButtons().add(continueB);
        quitB.setText("END GAME");
        buttons.getButtons().add(quitB);
    }

    @Override
    public void draw(Main main) {
        main.background(0, 0, 0);
        main.fill(255, 255, 255);
        main.textSize(main.height / 6);
        String s = "GAME\nPAUSED";
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2 - (main.height / 6));

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
        if (ev instanceof EventMouse.EventMouseClicked) {
            if (continueB.isInside(main.mouseX, main.mouseY)) {
                main.popState(1);
            } else if (quitB.isInside(main.mouseX, main.mouseY)) {
                main.popState(2);
                parent.showEndGameScreen(main);
            }
        }
    }

}
