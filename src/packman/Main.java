/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman;

import fisica.Fisica;
import java.util.Stack;
import packman.events.EventKeys;
import packman.events.EventMouse;
import packman.events.EventMouse.EventMouseClicked;
import packman.states.StateInit;
import processing.core.PApplet;

/**
 * The main, or 'launcher' class for the game. This is the one that extends
 * PApplet
 *
 * @author campbell
 */
public class Main extends PApplet {

    /**
     * State stack holds the list of different states. If this is empty, the
     * program ends.
     */
    private Stack<State> states;

    /**
     * The {@link StateInit} used as the first state. We keep it to allow us to
     * 'reset' all the states, if we need to.
     */
    private StateInit initState;

    /**
     * Used to remember if the mouse was pressed last frame. Used for sending
     * {@link EventMouseClicked} events.
     */
    private boolean wasMousePressed;

    /**
     * Set to indicate that debugging is enabled
     */
    public static final boolean DEBUG = false;

    /**
     * Main function Tells PApplet to start the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    ///////////////
    /**
     * Set everything up.
     * <br> * set up the stack
     * <br> * set up the {@link initState}
     * <br> * makes the frame resizeable, and fullscreens it.
     */
    @Override
    public void setup() {
        states = new Stack<>();
        initState = new StateInit();
        states.push(initState);
        size(displayWidth - 60, displayHeight - 60);

        Fisica.init(this); // start Fisica
    }

    /**
     * Tell the current state to draw, and exit if there are no states.
     *
     * Also send any events we need to handle.
     */
    @Override
    public void draw() {
        if (states.empty()) { // if the initState is gone, then exit.
            exit();
            return;
        }

        State s = states.peek(); // get the active State
        s.draw(this);

        if (mousePressed && !wasMousePressed) { // if the mouse is pressed now, but wasn't last frame.
            EventMouse event = new EventMouse.EventMouseClicked(mouseButton, mouseX, mouseY);
            s.handleEvent(this, event);
        }
        wasMousePressed = mousePressed;
    }

    /**
     * Push a state onto the stack Calls suspend on the existing state, and then
     * calls init on the new state.
     *
     * @param s The state to add
     */
    public void pushState(State s) {
        states.peek().suspend(this, s); // suspend the current state
        s.setup(this, states.peek()); // set up the new state
        states.push(s);
    }

    /**
     * Pops a given number of states.
     *
     * @param num the number of states to pop
     */
    public void popState(int num) {
        for (int i = 0; i < num; i++) {
            State s = states.pop();
            s.exit(this);
        }
    }

    /**
     * Get the {@link InitState}
     *
     * @return
     */
    public StateInit getInitState() {
        return initState;
    }

    /**
     * Handle a key press.
     */
    @Override
    public void keyPressed() {
        // make a EventKeyPressed, and send it to the active state.
        EventKeys event = new EventKeys.EventKeyPressed(key);
        states.peek().handleEvent(this, event);

        // We can stop Processing from closing when we press escape.
        // We do this by setting the key variable to 0, as processing handles
        // it's stuff after calling this function.
        if (key == ESC) {
            key = 0; // no excape key to exit.
        }
    }

    /**
     * Does the sketch run in full screen mode?
     *
     * @return {@code true}
     */
    @Override
    public boolean sketchFullScreen() {
        return true;
    }
}
