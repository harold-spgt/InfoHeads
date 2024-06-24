package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.repository.LocalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Tests the full serialization and deserialization process
 * from the {@link LocalRepository} class functions.
 */
public class LocalRepositoryIntegrationTest {

    @TempDir
    private Path tempDir;

    private final static String SUB_FOLDER = "local_repo_integration";

    @Test
    public void saveAndFetchIntegrationTest() {
        // Arrange
        Path parentFolderPath = Path.of(tempDir.toFile().getAbsolutePath(), SUB_FOLDER);
        LocalRepository<InfoHeadProperties> repository = new LocalRepository<>(parentFolderPath);
        UUID uuid = UUID.randomUUID();
        String name = "name";
        Location location = new Location(1,2,3, "");
        String permission = "permission";
        TimePeriod timePeriod = new TimePeriod(1,2,3,4,5);
        boolean oneTimeUse = true;
        InfoHeadProperties infoHeadProperties = new InfoHeadProperties(
                uuid,
                name,
                location,
                permission,
                timePeriod,
                oneTimeUse,
                true
        );

        // Act & Assert
        boolean didSave = repository.save(infoHeadProperties);
        Assertions.assertTrue(didSave, "Did not save.");

        File file = Path.of(parentFolderPath.toFile().getAbsolutePath(), infoHeadProperties.getId() + LocalRepository.getDataFileExtension()).toFile();
        Assertions.assertTrue(file.exists(), "File was not serialized or created.");

        List<InfoHeadProperties> infoHeadPropertiesList = repository.getAll();

        Assertions.assertEquals(1, infoHeadPropertiesList.size(), "Did not deserialize.");
        InfoHeadProperties deserialized = infoHeadPropertiesList.get(0);

        // Assert deserialized all fields correctly.
        Assertions.assertEquals(uuid, deserialized.getId());
        Assertions.assertEquals(name, deserialized.getName());
        Assertions.assertEquals(location, deserialized.getLocation());
        Assertions.assertEquals(permission, deserialized.getPermission());
        Assertions.assertEquals(timePeriod, deserialized.getCoolDown());
        Assertions.assertEquals(oneTimeUse, deserialized.isOneTimeUse());
    }
}
