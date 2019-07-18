package me.harry0198.infoheads.utils;

import org.bukkit.Material;

public enum MaterialUtils {

    PLAYER_HEAD("SKULL_ITEM");

    private enum MaterialVersion {
        v1_14,
        v1_13,
        v1_12
    }

    private static final MaterialVersion materialVersion = Material.getMaterial("GREEN_DYE") != null ? MaterialVersion.v1_14 : Material.getMaterial("WHITE_WOOL") != null ? MaterialVersion.v1_13 : MaterialVersion.v1_12;

    private String v1_13Material, v1_12Material;


    MaterialUtils(String v1_12Material) {
        this.v1_12Material = v1_12Material;
    }


    public Material getMaterial() {
        Material material = Material.getMaterial(materialVersion == MaterialVersion.v1_14 ? toString() : materialVersion == MaterialVersion.v1_13 ? v1_13Material : v1_12Material);

        if (material == null)
            material = Material.getMaterial(toString());

        if (material == null)
            material = Material.getMaterial(v1_12Material);

        if (material == null)
            material = Material.getMaterial(v1_13Material);

        if (material == null)
            material = Material.DIRT;

        return material;
    }
}
