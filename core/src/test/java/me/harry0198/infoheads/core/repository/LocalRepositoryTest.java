package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.repository.LocalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LocalRepositoryTest {

    @TempDir
    private Path tempDir;

    private final static Path TEST_FOLDER = Path.of("src","test", "resources", "repository", "local_repository", "unit");

    @Test
    public void doesSave() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        InfoHeadProperties infoHeadProperties = new InfoHeadProperties(
                uuid,
                "",
                new Location(1,2,3, "dimension"),
                "",
                new TimePeriod(1,2,3,4,5),
                true,
                true
        );

        // Act
        boolean didSave = new LocalRepository<InfoHeadProperties>(tempDir, InfoHeadProperties.class).save(infoHeadProperties);

        // Assert
        File file = Path.of(tempDir.toFile().getAbsolutePath(), infoHeadProperties.getId() + LocalRepository.getDataFileExtension()).toFile();

        Assertions.assertTrue(didSave);
        Assertions.assertTrue(file.exists());
    }

//    @Test
//    public void doesFetch() {
//        // Arrange
//        UUID expectedUUID = UUID.fromString("39e7bb24-fda3-4d8a-a3ea-4ef9999932f3");
//        String expectedName = "name";
//        Location expectedLocation = new Location(1,2,3, "");
//        String expectedPermission = "permission";
//        TimePeriod expectedTimePeriod = new TimePeriod(1,2,3,4,5);
//        boolean expectedOneTimeUse = true;
//        boolean expectedEnabled = true;
//
//        // Act
//        List<InfoHeadProperties> infoHeadPropertiesList = new LocalRepository<InfoHeadProperties>(Path.of(TEST_FOLDER.toFile().getAbsolutePath(), "all")).getAll();
//
//        // Assert
//        Assertions.assertEquals(1, infoHeadPropertiesList.size(), "Did not deserialize.");
//        InfoHeadProperties deserialized = infoHeadPropertiesList.get(0);
//
//        // Assert deserialized all fields correctly.
//        Assertions.assertEquals(expectedUUID, deserialized.getId());
//        Assertions.assertEquals(expectedName, deserialized.getName());
//        Assertions.assertEquals(expectedLocation, deserialized.getLocation());
//        Assertions.assertEquals(expectedPermission, deserialized.getPermission());
//        Assertions.assertEquals(expectedTimePeriod, deserialized.getCoolDown());
//        Assertions.assertEquals(expectedOneTimeUse, deserialized.isOneTimeUse());
//        Assertions.assertEquals(expectedEnabled, deserialized.isEnabled()); // TODO RESOLVE TEST (change test file).
//    }

    @Test
    public void doesDelete() throws IOException {
        // Arrange
        InfoHeadProperties infoHeadProperties = new InfoHeadProperties(
                UUID.fromString("39e7bb24-fda3-4d8a-a3ea-4ef9999932f3"),
                        null,
                        null,
                        null,
                        null,
                        false,
                true
        );
        // Copy over file that should be deleted.
        Path copyFile = Path.of(TEST_FOLDER.toFile().getAbsolutePath(), "delete", infoHeadProperties.getId() + LocalRepository.getDataFileExtension());
        Path copyTo = Path.of(tempDir.toFile().getAbsolutePath(), infoHeadProperties.getId() + LocalRepository.getDataFileExtension());
        Files.copy(copyFile, copyTo, REPLACE_EXISTING);
        Assertions.assertTrue(copyTo.toFile().exists(), "Could not copy file to temp dir.");

        // Act
        new LocalRepository<InfoHeadProperties>(tempDir, InfoHeadProperties.class).delete(infoHeadProperties);

        // Assert
        Assertions.assertFalse(copyTo.toFile().exists());
    }
}