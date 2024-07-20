package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.elements.DelayElement;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.ui.TimePeriodViewModel;

public class DelayViewModel extends TimePeriodViewModel {

    private TimePeriod initDelay;

    /**
     * Constructs this class.
     *
     * @param configuration Configuration which provides the {@link me.harry0198.infoheads.core.model.TimePeriod}.
     */
    public DelayViewModel(InfoHeadProperties configuration, TimePeriod initDelay) {
        super(configuration);
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
