import com.google.inject.Guice;
import me.harry0198.infoheads.core.di.CoreModule;
import me.harry0198.infoheads.spigot.SpigotInfoHeadsPlugin;
import me.harry0198.infoheads.spigot.di.SpigotModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// This file aims to test that the main "entry" point can correctly be built using the di framework.
// These tests aim to not test guice's functionality but that the dependencies have been correctly provided.
class InjectorTests {

    @Test
    void CanBuildSpigotInfoHeads() {
        // Act
        var injector = Guice.createInjector(new CoreModule(), new SpigotModule(null));
        var infoHeadsPlugin = injector.getInstance(SpigotInfoHeadsPlugin.class);

        Assertions.assertNotNull(infoHeadsPlugin);
    }
}
