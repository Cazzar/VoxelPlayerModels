package net.cazzar.mods.voxelplayers;

import java.io.Serializable;

/*
 * The Body class is made up of six serializable byte arrays, each being either
 * the palette index of the cube it represents, or -1 for no cube
 */


public class aBody implements Serializable {
    private static final long serialVersionUID = 8222649723331201258L;
    public byte[][][] headOffsets, torsoOffsets, leftArmOffsets, rightArmOffsets,
            leftLegOffsets, rightLegOffsets;
}
