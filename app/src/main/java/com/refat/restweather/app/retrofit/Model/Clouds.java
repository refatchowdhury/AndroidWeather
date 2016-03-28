package com.refat.restweather.app.retrofit.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by refat on 27/12/2015.
 */
public class Clouds {

    @Expose
    private Integer all;

    /**
     *
     * @return
     * The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }
}

