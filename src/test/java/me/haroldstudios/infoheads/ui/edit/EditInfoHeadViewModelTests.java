package me.haroldstudios.infoheads.ui.edit;

import com.haroldstudios.infoheads.ui.GuiSlot;
import com.haroldstudios.infoheads.ui.edit.EditInfoHeadViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests the {@link EditInfoHeadViewModel} class's functions and
 * behaviours such as the ProgressionSlots.
 */
public class EditInfoHeadViewModelTests {

    // Provide test data as a stream of arguments
    static Stream<Arguments> progressionTestData() {
        return Stream.of(
                Arguments.of(5, new GuiSlot[]{
                        new GuiSlot(2, 1),
                        new GuiSlot(3, 1),
                        new GuiSlot(4, 1),
                        new GuiSlot(5, 1),
                        new GuiSlot(5, 2),
                        new GuiSlot(5, 3),
                        new GuiSlot(4, 3),
                        new GuiSlot(3, 3),
                        new GuiSlot(2, 3),
                        new GuiSlot(2, 4),
                        new GuiSlot(2, 5),
                        new GuiSlot(3, 5),
                        new GuiSlot(4, 5),
                        new GuiSlot(5, 5)
                }),
                Arguments.of(1, new GuiSlot[] {
                        new GuiSlot(2, 1),
                        new GuiSlot(3, 1),
                        new GuiSlot(4, 1),
                        new GuiSlot(5, 1),
                }),
                Arguments.of(3, new GuiSlot[]{
                        new GuiSlot(2, 1),
                        new GuiSlot(3, 1),
                        new GuiSlot(4, 1),
                        new GuiSlot(5, 1),
                        new GuiSlot(5, 2),
                        new GuiSlot(5, 3),
                        new GuiSlot(4, 3),
                        new GuiSlot(3, 3),
                        new GuiSlot(2,3)
                }),
                Arguments.of(0, new GuiSlot[0]),
                Arguments.of(-500, new GuiSlot[0])
        );
    }

    /**
     * Tests the {@link EditInfoHeadViewModel#getProgressionSlots(int)} method correctly
     * generates the zigzag pattern with gui slots and adheres to the maximum column parameter.
     */
    @ParameterizedTest
    @MethodSource("progressionTestData")
    public void testProgressionSlots(int maxCols, GuiSlot[] expectedSlots) {
        // Arrange
        EditInfoHeadViewModel viewModel = new EditInfoHeadViewModel(null);

        // Act
        var actualSlots = viewModel.getProgressionSlots(maxCols);

        // Assert
        Assertions.assertEquals(expectedSlots.length, actualSlots.size(), "Actual slots length does not match the expected.");
        for (int i = 0; i < expectedSlots.length; i++) {
            GuiSlot expectedSlot = expectedSlots[i];
            GuiSlot actualSlot = actualSlots.removeFirst();
            Assertions.assertEquals(expectedSlot.row(), actualSlot.row(), String.format("Row mismatch at index {%d}. Expected: {%d}, Actual: {%d}", i, expectedSlot.row(), actualSlot.row()));
            Assertions.assertEquals(expectedSlot.col(), actualSlot.col(), String.format("Col mismatch at index {%d}. Expected: {%d}, Actual: {%d}", i, expectedSlot.col(), actualSlot.col()));
        }
    }
}
