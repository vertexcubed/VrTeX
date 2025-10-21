package vertexcubed.vrtex.client.screen;

import net.minecraft.client.gui.screens.Screen;
import vertexcubed.vrtex.common.task.TaskManager;

public class ScreenHelper {

    /**
     * Returns the associated TaskManager for this screen.
     * By default, all screens have a task manager associated with them.
     * Will only return null if this is called within the screen constructor (do not do!).
     */
    public static TaskManager getTaskManager(Screen screen) {
        return ((VrTeXScreen) screen).vrtex$getTaskManager();
    }
}