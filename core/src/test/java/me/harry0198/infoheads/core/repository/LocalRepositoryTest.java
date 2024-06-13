package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.TimePeriod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class LocalRepositoryTest {

    @TempDir
    private Path tempDir;

    private final static Path TEST_FOLDER = Path.of("src","test", "resources", "repository", "local_repository");

    @Test
    public void doesSave() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        InfoHeadProperties infoHeadProperties = new InfoHeadProperties(
                uuid,
                "",
                new Location(1,2,3, ""),
                "",
                new TimePeriod(1,2,3,4,5),
                true
        );

        // Act
        boolean didSave = new LocalRepository<InfoHeadProperties>(tempDir).save(infoHeadProperties);

        // Assert
        File file = Path.of(tempDir.toFile().getAbsolutePath(), "local_data", infoHeadProperties.getId() + ".dat").toFile();

        Assertions.assertTrue(didSave);
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void doesFetch() {
        // Arrange
        UUID expectedUUID = UUID.fromString("39e7bb24-fda3-4d8a-a3ea-4ef9999932f3");
        String expectedName = "name";
        Location expectedLocation = new Location(1,2,3, "");
        String expectedPermission = "permission";
        TimePeriod expectedTimePeriod = new TimePeriod(1,2,3,4,5);
        boolean expectedOneTimeUse = true;

        // Act
        List<InfoHeadProperties> infoHeadPropertiesList = new LocalRepository<InfoHeadProperties>(TEST_FOLDER).getAll();

        // Assert
        Assertions.assertEquals(1, infoHeadPropertiesList.size(), "Did not deserialize.");
        InfoHeadProperties deserialized = infoHeadPropertiesList.get(0);

        // Assert deserialized all fields correctly.
        Assertions.assertEquals(expectedUUID, deserialized.getId());
        Assertions.assertEquals(expectedName, deserialized.getName());
        Assertions.assertEquals(expectedLocation, deserialized.getLocation());
        Assertions.assertEquals(expectedPermission, deserialized.getPermission());
        Assertions.assertEquals(expectedTimePeriod, deserialized.getCoolDown());
        Assertions.assertEquals(expectedOneTimeUse, deserialized.isOneTimeUse());

    }
}