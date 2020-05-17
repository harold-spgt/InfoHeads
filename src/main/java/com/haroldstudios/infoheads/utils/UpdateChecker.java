package com.haroldstudios.infoheads.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    public static final String SONGODA_API = "https://songoda.com/api/products/infoheads-interact-with-your-own-blocks-skulls/";

    private static String readUrl() throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(SONGODA_API);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static void checkForUpdate() {
        String json;
        try {
            json = readUrl();
        } catch (Exception ignore) {
            InfoHeads.getInstance().info("Could not fetch updates");
            return;
        }

        Gson gson = InfoHeads.getInstance().getFileUtil().getGson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String latestVer = jsonObject.get("data").getAsJsonObject().get("jars").getAsJsonArray().get(0).getAsJsonObject().get("version").getAsString();

        Bukkit.getScheduler().runTask(InfoHeads.getInstance(), () -> {
            if (!InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(latestVer)) {
                InfoHeads.getInstance().info("There is a new update available. You can download it at §bhttps://songoda.com/marketplace/product/infoheads-interact-with-your-own-blocks-skulls.362");
            }
        });
    }
}
