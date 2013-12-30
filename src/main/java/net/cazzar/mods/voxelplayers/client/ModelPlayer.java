package net.cazzar.mods.voxelplayers.client;

import net.cazzar.mods.voxelplayers.VoxelPlayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import voxelplayermodels.Body;

public class ModelPlayer extends ModelBiped {

    ModelRenderer[][][] head;
    ModelRenderer[][][] torso;
    ModelRenderer[][][] leftArm;
    ModelRenderer[][][] rightArm;
    ModelRenderer[][][] leftLeg;
    ModelRenderer[][][] rightLeg;
    Body myBody;

    float scale, rotation;
    int width, height;

    public ModelPlayer() {
        this(0.0F);
    }

    public ModelPlayer(float par1) {
        this(par1, 0.0F, 64, 32);
    }

    public ModelPlayer(float par1, float par2, int par3, int par4) {
        this.scale = par1;
        this.rotation = par2;
        this.width = par3;
        this.height = par4;
    }

    public void setUpCustom(String name) {
        myBody = VoxelPlayers.proxy.playerBodies.get(name);

        if (myBody == null && name.equals(Minecraft.getMinecraft().thePlayer.func_146103_bH().getName())) {
            myBody = Serializer.deserializeBody(System.getenv("APPDATA") + "\\.minecraft\\models\\" + name + ".body");
            VoxelPlayers.proxy.playerBodies.put(name, myBody);
        }

        //If we REALLY failed
        if (myBody == null) {
            setUpDefault();
//            VoxelPlayers.proxy.playerBodies.put(name, myBody);
            return;
        }

        head = new ModelRenderer[14][14][14];
        torso = new ModelRenderer[14][18][10];
        leftArm = new ModelRenderer[10][18][10];
        rightArm = new ModelRenderer[10][18][10];
        leftLeg = new ModelRenderer[10][18][10];
        rightLeg = new ModelRenderer[10][18][10];

        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.aimedBow = false;
        this.textureWidth = width;
        this.textureHeight = height;
        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, scale);

        this.bipedHead = new ModelRenderer(this, 0, 0);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                for (int k = 0; k < 14; k++) {
                    if (myBody.headOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.headOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        head[i][j][k] = new ModelRenderer(this, startx, starty);
                        head[i][j][k].addBox(-i + 6, -j + 2, k - 7, 1, 1, 1, scale);
                        bipedHead.addChild(head[i][j][k]);
                    }
                }
            }

        }
        //this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);

        //this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        //this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
        //this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);

        this.bipedBody = new ModelRenderer(this, 16, 16);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 18; j++) {
                for (int k = 0; k < 10; k++) {
                    if (myBody.torsoOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.torsoOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        torso[i][j][k] = new ModelRenderer(this, startx, starty);
                        torso[i][j][k].addBox(-i + 6, -j + 14, k - 5, 1, 1, 1, scale);
                        bipedBody.addChild(torso[i][j][k]);
                    }
                }
            }

        }
        //this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                for (int k = 0; k < 10; k++) {
                    if (myBody.rightArmOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.rightArmOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        rightArm[i][j][k] = new ModelRenderer(this, startx, starty);
                        rightArm[i][j][k].addBox(-i + 3, -j + 12, k - 5, 1, 1, 1, scale);
                        bipedRightArm.addChild(rightArm[i][j][k]);
                    }
                }
            }

        }
        //this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, par1);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + rotation, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                for (int k = 0; k < 10; k++) {
                    if (myBody.leftArmOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.leftArmOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        leftArm[i][j][k] = new ModelRenderer(this, startx, starty);
                        leftArm[i][j][k].addBox(-i + 5, -j + 12, k - 5, 1, 1, 1, scale);
                        bipedLeftArm.addChild(leftArm[i][j][k]);
                    }
                }
            }

        }
        //this.bipedLeftArm.mirror = true;
        //this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, par1);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + rotation, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                for (int k = 0; k < 10; k++) {
                    if (myBody.rightLegOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.rightLegOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        rightLeg[i][j][k] = new ModelRenderer(this, startx, starty);
                        rightLeg[i][j][k].addBox(-i + 4, -j + 14, k - 5, 1, 1, 1, scale);
                        bipedRightLeg.addChild(rightLeg[i][j][k]);
                    }
                }
            }

        }
        //this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + rotation, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                for (int k = 0; k < 10; k++) {
                    if (myBody.leftLegOffsets[i][j][k] != -1) {

                        int startx = 0, starty = 0;
                        switch (myBody.leftLegOffsets[i][j][k]) {
                            //the first row
                            case 0:
                                startx = 24;
                                starty = 0;
                                break;
                            case 1:
                                startx = 28;
                                starty = 0;
                                break;
                            case 2:
                                startx = 32;
                                starty = 0;
                                break;
                            case 3:
                                startx = 36;
                                starty = 0;
                                break;
                            case 4:
                                startx = 56;
                                starty = 0;
                                break;
                            case 5:
                                startx = 60;
                                starty = 0;
                                break;
                            case 6:
                                startx = 24;
                                starty = 2;
                                break;
                            case 7:
                                startx = 28;
                                starty = 2;
                                break;

                            //the second row
                            case 8:
                                startx = 32;
                                starty = 2;
                                break;
                            case 9:
                                startx = 36;
                                starty = 2;
                                break;
                            case 10:
                                startx = 56;
                                starty = 2;
                                break;
                            case 11:
                                startx = 60;
                                starty = 2;
                                break;
                            case 12:
                                startx = 24;
                                starty = 4;
                                break;
                            case 13:
                                startx = 28;
                                starty = 4;
                                break;
                            case 14:
                                startx = 32;
                                starty = 4;
                                break;
                            case 15:
                                startx = 36;
                                starty = 4;
                                break;

                            //the third row
                            case 16:
                                startx = 56;
                                starty = 4;
                                break;
                            case 17:
                                startx = 60;
                                starty = 4;
                                break;
                            case 18:
                                startx = 24;
                                starty = 6;
                                break;
                            case 19:
                                startx = 28;
                                starty = 6;
                                break;
                            case 20:
                                startx = 32;
                                starty = 6;
                                break;
                            case 21:
                                startx = 36;
                                starty = 6;
                                break;
                            case 22:
                                startx = 56;
                                starty = 6;
                                break;
                            case 23:
                                startx = 60;
                                starty = 6;
                                break;
                        }

                        leftLeg[i][j][k] = new ModelRenderer(this, startx, starty);
                        leftLeg[i][j][k].addBox(-i + 4, -j + 14, k - 5, 1, 1, 1, scale);
                        bipedLeftLeg.addChild(leftLeg[i][j][k]);
                    }
                }
            }

        }
        //this.bipedLeftLeg.mirror = true;
        //this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + rotation, 0.0F);

        this.bipedHeadwear.cubeList.clear();
    }

    public void setUpDefault() {
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.aimedBow = false;
        this.textureWidth = width;
        this.textureHeight = height;
        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, scale);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + rotation, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + rotation, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + rotation, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + rotation, 0.0F);
    }

}
