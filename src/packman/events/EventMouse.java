/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.events;

import packman.Event;

/**
 * Abstract representation of any mouse event.
 * @author campbell
 */
public abstract class EventMouse implements Event {

    /**
     * The X position of the mouse when this event was created.
     */
    public final int x;
    
    /**
     * The Y position of the mouse when this event was created.
     */
    public final int y;

    public EventMouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Represents a mouse click.
     * This is sent when the mouse button is depressed over the main window.
     */
    public static class EventMouseClicked extends EventMouse {
        
        /**
         * Represents the mouse button pressed.
         */
        public final int button;

        /**
         * Creates a new {@link EventMouseClicked}
         * @param button The mouse button pressed.
         * @param x The X position of the mouse
         * @param y The Y position of the mouse
         */
        public EventMouseClicked(int button, int x, int y) {
            super(x, y);
            this.button = button;
        }
    }

}
