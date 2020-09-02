package com.zhouweixian.instadownloader.interfaces;

import com.zhouweixian.instadownloader.models.Instagram;

/**
 * @author Ican Bachors
 * @version 1.1
 * Source: https://github.com/bachors/Insta-Downloader
 */

public interface InstaListener {
    void onResponse(Instagram instagram);
    void onFailure(String message);
}
