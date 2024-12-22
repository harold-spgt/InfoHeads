package me.harry0198.infoheads.core.ui;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.elements.DelayElement;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class DelayViewModel extends TimePeriodViewModel {

    private TimePeriod initDelay;

    /**
     * Constructs this class.
     *
     * @param configuration Configuration which provides the {@link me.harry0198.infoheads.core.model.TimePeriod}.
     */
    @Inject
    public DelayViewModel(InfoHeadProperties configuration, EventDispatcher eventDispatcher, TimePeriod initDelay) {
        super(configuration, eventDispatcher);
        this.initDelay = initDelay;
    }

    @Override
    public void initialize() {
        // Ensure not null.
        if (initDelay == null) {
            initDelay = new TimePeriod(0,0,0,0,0);
        }

        getTimePeriodProperty().setValue(initDelay);
    }

    @Override
    public void saveConfiguration() {
        getConfiguration().addElement(new DelayElement(getTimePeriodProperty().getValue()));
        requestClose();
    }
}
