package me.harry0198.infoheads.spigot.ui;

import me.harry0198.infoheads.core.elements.DelayElement;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.ui.TimePeriodViewModel;

public class DelayViewModel extends TimePeriodViewModel {

    /**
     * Constructs this class.
     *
     * @param configuration Configuration which provides the {@link me.harry0198.infoheads.core.model.TimePeriod}.
     */
    public DelayViewModel(InfoHeadProperties configuration) {
        super(configuration);
    }

    @Override
    public void saveConfiguration() {
        getConfiguration().addElement(new DelayElement(getTimePeriodProperty().getValue()));
        requestClose();
    }
}
