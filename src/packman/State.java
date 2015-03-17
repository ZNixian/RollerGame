/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

/**
 * Represents a given game state.
 * @author campbell
 */
public interface State {
    /**
     * Sets the state up
     * @param main The main {@link PApplet} object.
     * @param s The state that used to be active. This will <b>never</b> be null - see {@link StateInit}.
     */
    void setup(Main main, State s);
    
    /**
     * Draws this state to the screen.
     * Only called when this state is the active state.
     * @param main The main object.
     */
    void draw(Main main);
    
    /**
     * Called when this state is popped off the state stack.
     * @param main 
     */
    void exit(Main main);
    
    /**
     * Suspend this state.
     * Called when a new state is pushed over the top this state.
     * @param main The main object.
     * @param s The state being pushed on top.
     */
    void suspend(Main main, State s);
    
    /**
     * Handle an event when it is dispatched.
     * Only called when this state is the active state.
     * @param main The main object.
     * @param ev The event that is being sent.
     */
    void handleEvent(Main main, Event ev);
}
