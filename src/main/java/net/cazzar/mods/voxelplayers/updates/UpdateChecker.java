package net.cazzar.mods.voxelplayers.updates;

import com.google.gson.Gson;
import net.cazzar.mods.voxelplayers.VoxelPlayers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static net.cazzar.mods.voxelplayers.updates.UpdateChecker.Status.*;

public class UpdateChecker implements Runnable {
    public static class UpdateInfo {
        public String minecraft;
        public String version;
        public List<String> changes;

        public int[] getNumbers() {
            String[] parts = version.split(".");
            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) numbers[i] = Integer.valueOf(parts[i]);

            return numbers;
        }
    }

    public enum Status {
        UNKNOWN, ERROR, UPDATE_MAJOR, UPDATE_MINOR, UPDATE_REVISION, UPDATE_BUILD, UPDATE_OTHER, OKAY
    }

    //    private static Map<String, UpdateChecker> updaters = Maps.newHashMap();
    private static UpdateChecker checker;
    URL downloadUrl;

    private UpdateInfo updateInfo;
    private Status status = UNKNOWN;

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            updateInfo = gson.fromJson(new InputStreamReader(downloadUrl.openStream()), UpdateInfo.class);

            String version = VoxelPlayers.instance.getVersion();
            if (version.equals("UNKNOWN")) {
                status = UPDATE_MAJOR;
                return;
            }

            String[] parts = version.split(".");
            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) numbers[i] = Integer.valueOf(parts[i]);

            int[] updatedNumbers = updateInfo.getNumbers();
            for (int i = 0; i < numbers.length; i++) {
                if (updatedNumbers[i] > numbers[i]) {
                    switch (i) {
                        case 0:
                            status = UPDATE_MAJOR;
                            break;
                        case 1:
                            status = UPDATE_MINOR;
                            break;
                        case 2:
                            status = UPDATE_REVISION;
                            break;
                        case 3:
                            status = UPDATE_BUILD;
                            break;
                        default:
                            status = UPDATE_OTHER;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UpdateChecker(String modid) {
        try {
            downloadUrl = new URL("http://direct.cazzar.net/version.php?repo=" + modid + "&text=1");


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UpdateChecker instance(String modid) {
        if (checker == null) checker = new UpdateChecker(modid);

        return checker;
    }

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public Status getStatus() {
        return status;
    }
}
