package com.haroldstudios.infoheads.hooks;

import me.arcaniax.hdb.api.HeadDatabaseAPI;

public final class HdbHook {

    private HeadDatabaseAPI hdbApi = null;

    public HeadDatabaseAPI getHdbApi() {
        return hdbApi;
    }

    public void setHdbApi(HeadDatabaseAPI hdbApi){
        this.hdbApi = hdbApi;
    }


}

