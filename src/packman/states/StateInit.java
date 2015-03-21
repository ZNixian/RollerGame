/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Event;
import packman.Main;
import packman.State;

/**
 * The state at the bottom of the state stack. When this state closes, the game shuts down.
 * This is partly a convenience thing for other states, so functions like
 * {@link State#setup(packman.Main, packman.State)} work, without {@code null} for a initial state.
 * @author campbell
 */
public class StateInit implements State {
    
    private State nextState = new StateMenu();

    @Override
    public void setup(Main main, State s) {
        main.pushState(new StateMenu());
    }

    @Override
    public void draw(Main main) {
        if(nextState != null) {
            main.pushState(nextState);
            nextState = null;
        } else {
            main.popState(1);
        }
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

}
