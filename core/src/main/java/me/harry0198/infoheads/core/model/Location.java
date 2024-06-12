package me.harry0198.infoheads.core.model;

/**
 * Defines a location point in a 3d space and dimension.
 * @param x coordinate.
 * @param y coordinate.
 * @param z coordinate.
 * @param dimension of location.
 */
public record Location(
        double x,
        double y,
        double z,
        String dimension
) {
}
