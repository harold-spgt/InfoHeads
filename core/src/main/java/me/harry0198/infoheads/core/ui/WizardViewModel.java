package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.utils.SimpleProperty;

public class WizardViewModel extends ViewModel {
    private final InfoHeadProperties configuration;
    private final SimpleProperty<Boolean> isOneTimeUseProperty;
    private final SimpleProperty<Boolean> shouldOpenEditGui = new SimpleProperty<>(false);
    private final SimpleProperty<Boolean> shouldOpenCooldownGui = new SimpleProperty<>(false);

    public WizardViewModel(InfoHeadProperties configuration) {
        this.configuration = configuration;
        this.isOneTimeUseProperty = new SimpleProperty<>(configuration.isOneTimeUse());
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

    public InfoHeadProperties getConfiguration() {
        return configuration;
    }

//    public void startConversing(Element.InfoHeadType infoHeadType, HumanEntity humanEntity) {
//        if (humanEntity instanceof Player player) {
//            getShouldCloseProperty().setValue(true);
//            infoHeads.getInputFactory(configuration, infoHeadType).buildConversation(player).begin();
//        }
//    }
//
//    public void editName(HumanEntity humanEntity) {
//        if (humanEntity instanceof Player player) {
//            getShouldCloseProperty().setValue(true);
//            infoHeads.getInputFactory(configuration, Element.InfoHeadType.RENAME).buildConversation(player).begin();
//        }
//    }
//
//    public void setOneTimeUse(boolean oneTimeUse) {
//        configuration.getExecuted().clear();
//        configuration.setOnce(!oneTimeUse);
//        isOneTimeUseProperty.setValue(oneTimeUse);
//    }
//
//    public void editItem() {
//        shouldOpenEditGui.setValue(true);
//    }
//
//    public void editCooldown() {
//        shouldOpenCooldownGui.setValue(true);
//    }
//
//    public void setLocation(HumanEntity humanEntity) {
//        if (!(humanEntity instanceof Player player)) return;
//
//        getShouldCloseProperty().setValue(true);
//        MessageUtil.sendMessage(player, MessageUtil.Message.PLACE_INFOHEAD);
//        DataStore.placerMode.put(player, configuration); //TODO unholy use of static jesus christ this is asking for problems!
//
//        HeadStacks.giveHeads(player);
//    }
}
