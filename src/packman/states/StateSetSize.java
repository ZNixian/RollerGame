/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Event;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 *
 * @author campbell
 */
public class StateSetSize implements State {

    @Override
    public void setup(Main main, State s) {
    }

    @Override
    public void draw(Main main) {
        main.background(255, 255, 255);
        main.fill(0, 0, 0);
        main.textSize(50);
        String s = "Resize,\nthen click";
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2);
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
            main.frame.setResizable(false);
            main.popState(1);
            main.pushState(new StateMenu());
        }
    }

}
