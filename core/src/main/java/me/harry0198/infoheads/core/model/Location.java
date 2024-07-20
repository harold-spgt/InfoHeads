package me.harry0198.infoheads.core.model;

import java.io.Serializable;

/**
 * Defines a location point in a 3d space and dimension.
 */
public record Location(int x, int y, int z, String dimension) implements Serializable {
}
