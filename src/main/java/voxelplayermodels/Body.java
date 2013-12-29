package voxelplayermodels;

import java.io.Serializable;

//this is just a compat class for
public class Body implements Serializable {
    private static final long serialVersionUID = 8222649723331201258L;
    public byte[][][] headOffsets, torsoOffsets, leftArmOffsets, rightArmOffsets,
            leftLegOffsets, rightLegOffsets;
}
