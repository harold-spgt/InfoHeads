package me.harry0198.infoheads.core.event;

public interface InfoHeadEventListenerRegister {

    void registerSendPlayerMessageListener();
    void registerRemoveTempPermissionListener();
    void registerSendPlayerCommandListener();
    void registerSendConsoleCommandListener();
    void registerApplyTempPlayerPermissionListener();
    void registerOpenMenuListener();
    void registerOpenAddActionMenuListener();
    void registerGetConsoleCommandInputListener();
    void registerGetPlayerCommandInputListener();
    void registerGetMessageInputListener();
    void registerGetPlayerPermissionInputListener();
    void registerGetPermissionInputListener();
    void registerGetDelayInputListener();
    void registerGetCoolDownInputListener();
    void registerGetNameInputListener();
    void registerShowInfoHeadListListener();
    void registerGivePlayerHeadsListener();
}
