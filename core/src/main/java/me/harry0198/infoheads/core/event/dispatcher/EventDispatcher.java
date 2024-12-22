package me.harry0198.infoheads.core.event.dispatcher;

import me.harry0198.infoheads.core.event.types.Event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("squid:S6548")
public class EventDispatcher {
    private static final Logger LOGGER = Logger.getLogger(EventDispatcher.class.getName());
    private final Map<Class<? extends Event>, Set<EventListener<? extends Event>>> listenersMap = new HashMap<>();

    public <T extends Event> void registerListener(Class<T> eventType, EventListener<T> listener) {
        listenersMap.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    public void unregisterAll() {
        LOGGER.info("Unregistering all event listeners");
        listenersMap.clear();
    }

    public <T extends Event> void dispatchEvent(T event) {
        Set<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());
        if (listeners != null) {
            if (listeners.isEmpty()) {
                LOGGER.info("No handlers found for event: " + event.getClass());
            }

            for (EventListener<? extends Event> listener : listeners) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }
    }
}
