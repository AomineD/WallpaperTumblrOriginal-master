package com.gay.xmen.api;

import com.gay.xmen.models.TumblrItem;

import java.util.ArrayList;

public interface ResponseApi {
    void Correct(ArrayList<TumblrItem> response);

    void Incorrect(String errno);
}
