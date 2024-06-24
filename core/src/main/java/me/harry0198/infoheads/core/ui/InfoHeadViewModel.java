package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.inputs.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.SimpleProperty;

public class InfoHeadViewModel extends ViewModel {
    private final InfoHeadProperties configuration;
    private final SimpleProperty<Boolean> isOneTimeUseProperty;

    public InfoHeadViewModel(EventDispatcher eventDispatcher, InfoHeadProperties configuration) {
        super(eventDispatcher);
        this.configuration = configuration;
        this.isOneTimeUseProperty = new SimpleProperty<>(configuration.isOneTimeUse());
    }

    public SimpleProperty<Boolean> getIsOneTimeUseProperty() {
        return isOneTimeUseProperty;
    }

    public InfoHeadProperties getConfiguration() {
        return configuration;
    }

    public void newElement(Element.InfoHeadType infoHeadType, OnlinePlayer onlinePlayer) {
        switch (infoHeadType) {
            case MESSAGE -> getEventDispatcher().dispatchEvent(new GetMessageInputEvent(configuration));
            case PLAYER_COMMAND -> getEventDispatcher().dispatchEvent(new GetPlayerCommandInputEvent(configuration));
            case DELAY -> getEventDispatcher().dispatchEvent(new GetDelayInputEvent(configuration));
            case CONSOLE_COMMAND -> getEventDispatcher().dispatchEvent(new GetConsoleCommandInputEvent(configuration));
            case PERMISSION -> getEventDispatcher().dispatchEvent(new GetPermissionInputEvent(configuration));
            case PLAYER_PERMISSION -> getEventDispatcher().dispatchEvent(new GetPlayerPermissionInputEvent(configuration));
        }
    }

    public void rename(OnlinePlayer onlinePlayer) {
        getEventDispatcher().dispatchEvent(new GetNameInputEvent(configuration));
    }

    public void getCoolDownInput(OnlinePlayer onlinePlayer) {
        getEventDispatcher().dispatchEvent(new GetCoolDownInputEvent(configuration));
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
    public void setOneTimeUse(boolean oneTimeUse) {
        configuration.setOneTimeUse(!oneTimeUse);
        isOneTimeUseProperty.setValue(oneTimeUse);
    }
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
