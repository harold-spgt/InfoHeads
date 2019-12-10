package me.harry0198.infoheads.utils;

import me.arcaniax.hdb.api.HeadDatabaseAPI;

public class HdbApi {

    private HeadDatabaseAPI hdbApi = null;

    public HeadDatabaseAPI getHdbApi(){
        return hdbApi;
    }

    public void setHdbApi(HeadDatabaseAPI hdbApi){
        this.hdbApi = hdbApi;
    }

}
