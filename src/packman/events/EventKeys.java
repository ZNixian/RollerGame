/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.events;

import packman.Event;

/**
 * An abstract base for every event involving keys.
 * @author campbell
 * @see EventKeyPressed
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
     * Represents a key press.
     * This is sent when a key is pressed.
     */
    public static class EventKeyPressed extends EventKeys {

        public EventKeyPressed(char keycode) {
            super(keycode);
        }
    }

}
