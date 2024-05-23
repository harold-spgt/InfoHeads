package com.haroldstudios.infoheads.ui;

public record GuiSlot(
        int row,
        int col
) {

    public int getSlotFromRowCol() {
        return (col + (row - 1) * 9) - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiSlot guiSlot)) return false;
        return guiSlot.row == row && guiSlot.col == col;
    }
}
