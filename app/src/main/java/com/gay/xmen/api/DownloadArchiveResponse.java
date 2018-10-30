package com.gay.xmen.api;

import android.net.Uri;

public interface DownloadArchiveResponse {

    void Donwloaded(Uri path);

    void Failed(String errno);
}
