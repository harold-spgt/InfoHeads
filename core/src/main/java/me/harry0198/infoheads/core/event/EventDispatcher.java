package me.harry0198.infoheads.core.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventDispatcher {
    private static EventDispatcher instance;
    private final Map<Class<? extends Event>, Set<EventListener<? extends Event>>> listenersMap = new HashMap<>();

    private EventDispatcher() {}

    public static synchronized EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }
        return instance;
    }

    public <T extends Event> void registerListener(Class<T> eventType, EventListener<T> listener) {
        listenersMap.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    public <T extends Event> void dispatchEvent(T event) {
        Set<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());
        if (listeners != null) {
            for (EventListener<? extends Event> listener : listeners) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }
    }
}
