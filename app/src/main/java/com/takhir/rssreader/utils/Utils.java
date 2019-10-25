package com.takhir.rssreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)(context).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }

    public static String checkUrl(String url) {
        // "https://lenta.ru/rss/news/"
        // https://habr.com/ru/rss/interesting/
        // https://www.sports.ru/rss/all_news.xml/
        StringBuilder sb = new StringBuilder();
        if (!url.contains("https://")) {
            sb.append("https://");
        }

        if(url.charAt(url.length() - 1) != '/') {
            sb.append(url).append("/");
        } else {
            sb.append(url);
        }
        return sb.toString();
    }
}
