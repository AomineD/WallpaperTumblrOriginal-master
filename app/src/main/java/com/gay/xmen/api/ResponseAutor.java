package com.gay.xmen.api;

import com.gay.xmen.models.TumblrAutor;

public interface ResponseAutor {
    void OnDataLoaded(TumblrAutor autor);

    void OnDataFailedToLoad(String error);
}
