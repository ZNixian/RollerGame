/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.events;

import packman.Event;

/**
 *
 * @author campbell
 */
public abstract class EventKeys implements Event {

    /**
     * The key code.
     */
    public final char key;

    public EventKeys(char keycode) {
        this.key = keycode;
    }

    /**
     * Represents a mouse click.
     * This is sent when the mouse button is depressed over the main window.
     */
    public static class EventKeyPressed extends EventKeys {

        public EventKeyPressed(char keycode) {
            super(keycode);
        }
    }

}
