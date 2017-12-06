package com.example.carolineboeykens.mobileseriedatabase;

/**
 * Created by carolineboeykens on 06/12/2017.
 */

public class UserSerie {

    int userId;
    int serieId;

    public UserSerie(int userId, int serieId){
        this.userId = userId;
        this.serieId = serieId;

    }

    public int userId(){
        return userId;
    }

    public int serieId(){
        return serieId;
    }

}
