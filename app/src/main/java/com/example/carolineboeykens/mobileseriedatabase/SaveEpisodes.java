package com.example.carolineboeykens.mobileseriedatabase;


public class SaveEpisodes {
    public String Id;
    public int episodeId;
    public String userId;

    public SaveEpisodes(String Id, int episodeId, String userId){
        this.Id = Id;
        this.episodeId = episodeId;
        this.userId = userId;
    }
}
