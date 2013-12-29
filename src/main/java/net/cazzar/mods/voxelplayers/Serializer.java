package net.cazzar.mods.voxelplayers;

import voxelplayermodels.Body;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Serializer {
    public static Body deserializeBody(File path) {
        Body toReturn = null;

//        Gzip
        try {
            if (path.toString().endsWith(".body")) {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                toReturn = (Body) in.readObject();
                in.close();
                fileIn.close();
            }
        } catch (Exception e) {//if there was no file to load
        	System.out.println(e);
        }
        return toReturn;
    }

    public static Body deserializeBody(String path) {
        return deserializeBody(new File(path));
    }
}
