package me.harry0198.infoheads.core.event;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.handlers.PlaceHandler;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlaceHandlerTest {

    @Mock
    private InfoHeadService infoHeadService;

    @Mock
    private LocalizedMessageService localizedMessageService;

    @Mock
    private OnlinePlayer player;

    @InjectMocks
    private PlaceHandler placeHandler;

    private Location location;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        location = new Location(100, 64, 200, "world");
    }

    @Test
    public void testPlaceHead_WithNullInfoheadUUID() {
        // Arrange
        when(infoHeadService.addInfoHead(any(InfoHeadProperties.class)))
                .thenReturn(CompletableFuture.completedFuture(true));

        // Act
        placeHandler.placeHead(player, location, null);

        // Assert
        verify(infoHeadService, times(1)).addInfoHead(any(InfoHeadProperties.class));
    }

    @Test
    public void testPlaceHead_WithExistingInfoheadUUID() {
        // Arrange
        UUID existingUUID = UUID.randomUUID();
        InfoHeadProperties existingProperties = new InfoHeadProperties(existingUUID, "{}", location, null, null, false, false);
        when(infoHeadService.getInfoHead(existingUUID)).thenReturn(Optional.of(existingProperties));
        when(infoHeadService.addInfoHead(any(InfoHeadProperties.class)))
                .thenReturn(CompletableFuture.completedFuture(true));

        // Act
        placeHandler.placeHead(player, location, existingUUID);

        // Assert
        verify(infoHeadService, times(1)).getInfoHead(existingUUID);
        verify(infoHeadService, times(1)).addInfoHead(existingProperties);
    }

    @Test
    public void testPlaceHead_FailureToAddInfoHead() {
        // Arrange
        when(infoHeadService.addInfoHead(any(InfoHeadProperties.class)))
                .thenReturn(CompletableFuture.completedFuture(false));

        // Act
        placeHandler.placeHead(player, location, null);

        // Assert
        verify(player, times(1)).sendMessage(localizedMessageService.getMessage(BundleMessages.FAILED_TO_ADD));
    }

    @Test
    public void testPlaceHead_ExceptionWhileAddingInfoHead() {
        // Arrange
        when(infoHeadService.addInfoHead(any(InfoHeadProperties.class)))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Exception")));

        // Act
        placeHandler.placeHead(player, location, null);

        // Assert
        verify(player, times(1)).sendMessage(localizedMessageService.getMessage(BundleMessages.FAILED_TO_ADD));
    }
}