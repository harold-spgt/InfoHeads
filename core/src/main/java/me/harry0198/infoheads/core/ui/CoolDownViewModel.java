package me.harry0198.infoheads.core.ui;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class CoolDownViewModel extends TimePeriodViewModel {
    /**
     * Constructs this class.
     *
     * @param configuration Configuration which provides the {@link TimePeriod}.
     */
    @Inject
    public CoolDownViewModel(InfoHeadProperties configuration, EventDispatcher eventDispatcher) {
        super(configuration, eventDispatcher);
    }

    @Override
    public void initialize() {
        // Ensure not null.
        if (getConfiguration().getCoolDown() == null) {
            getConfiguration().setCoolDown(new TimePeriod(0,0,0,0,0));
        }

        getTimePeriodProperty().setValue(getConfiguration().getCoolDown());
    }

    @Override
    public void saveConfiguration() {
        getConfiguration().setCoolDown(getTimePeriodProperty().getValue());
        requestClose();
    }
}
