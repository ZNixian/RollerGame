/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Button;
import packman.ButtonManager;
import packman.Event;
import packman.GameModes;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 * Title screen menu.
 *
 * @author campbell
 */
public class StateMenu implements State {

    private final Button play = new Button();
    private final Button modeChanger = new Button();
    private final Button quit = new Button();
    private GameModes mode = GameModes.VERY_EASY;
    private ButtonManager manager = new ButtonManager();

    @Override
    public void setup(Main main, State s) {
        manager.getButtons().add(play);
        play.setText("PLAY");
        manager.getButtons().add(modeChanger);
        manager.getButtons().add(quit);
        quit.setText("QUIT GAME");
    }

    @Override
    public void draw(Main main) {
        main.background(0, 0, 0);
        main.fill(255, 255, 255);
        main.textSize(main.height / 6);
        String s = "Roller";
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2);

        ///////
        s = mode.toString().replace('_', ' ');
        modeChanger.setText(s);
        
        manager.draw(main.height / 2 + 25, main);
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
            if (play.isInside(main.mouseX, main.mouseY)) {
                main.pushState(new StateInGame(mode));
            } else if (modeChanger.isInside(main.mouseX, main.mouseY)) {
                int id = mode.ordinal();
                id = (id + 1) % GameModes.values().length;
                mode = GameModes.values()[id];
            } else if (quit.isInside(main.mouseX, main.mouseY)) {
                main.popState(1);
            }
        }
    }

}
