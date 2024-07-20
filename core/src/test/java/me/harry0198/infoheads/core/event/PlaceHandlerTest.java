package me.harry0198.infoheads.core.event;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.handlers.PlaceHandler;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

}