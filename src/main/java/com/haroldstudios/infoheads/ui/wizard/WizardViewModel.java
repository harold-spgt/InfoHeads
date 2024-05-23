package com.haroldstudios.infoheads.ui.wizard;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.ui.SimpleProperty;
import com.haroldstudios.infoheads.ui.ViewModel;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class WizardViewModel extends ViewModel {
    private final InfoHeads infoHeads;
    private final InfoHeadConfiguration configuration;
    private final SimpleProperty<Boolean> isOneTimeUseProperty;
    private final SimpleProperty<Boolean> shouldOpenEditGui = new SimpleProperty<>(false);
    private final SimpleProperty<Boolean> shouldOpenCooldownGui = new SimpleProperty<>(false);

    public WizardViewModel(InfoHeads infoHeads, InfoHeadConfiguration configuration) {
        this.infoHeads = infoHeads;
        this.configuration = configuration;
        this.isOneTimeUseProperty = new SimpleProperty<>(configuration.isOnce());
    }

    public SimpleProperty<Boolean> getShouldOpenEditGui() {
        return shouldOpenEditGui;
    }

    public SimpleProperty<Boolean> getIsOneTimeUseProperty() {
        return isOneTimeUseProperty;
    }

    public SimpleProperty<Boolean> getShouldOpenCooldownGui() {
        return shouldOpenCooldownGui;
    }

    public InfoHeadConfiguration getConfiguration() {
        return configuration;
    }

    public void startConversing(Element.InfoHeadType infoHeadType, HumanEntity humanEntity) {
        if (humanEntity instanceof Player player) {
            getShouldCloseProperty().setValue(true);
            infoHeads.getInputFactory(configuration, infoHeadType).buildConversation(player).begin();
        }
    }

    public void editName(HumanEntity humanEntity) {
        if (humanEntity instanceof Player player) {
            getShouldCloseProperty().setValue(true);
            infoHeads.getInputFactory(configuration).buildConversation(player).begin();
        }
    }

    public void setOneTimeUse(boolean oneTimeUse) {
        configuration.getExecuted().clear();
        configuration.setOnce(!oneTimeUse);
        setOneTimeUse(oneTimeUse);
    }

    public void editItem() {
        shouldOpenEditGui.setValue(true);
    }

    public void editCooldown() {
        shouldOpenCooldownGui.setValue(true);
    }

    public void setLocation(HumanEntity humanEntity) {
        if (!(humanEntity instanceof Player player)) return;

        getShouldCloseProperty().setValue(true);
        MessageUtil.sendMessage(player, MessageUtil.Message.PLACE_INFOHEAD);
        DataStore.placerMode.put(player, configuration); //TODO unholy use of static jesus christ this is asking for problems!

        HeadStacks.giveHeads(player);
    }
}
