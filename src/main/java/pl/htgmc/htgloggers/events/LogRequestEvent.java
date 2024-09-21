package pl.htgmc.htgloggers.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LogRequestEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String pluginName;

    public LogRequestEvent(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
