package com.example.android.mobilization;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Fetcher {
    private static final String TAG = "Fetchr";
    private static final String URL =
            "http://cache-default06g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Artist> fetchData() {
        return downloadData(URL);
    }

    private List<Artist> downloadData(String url) {
        List<Artist> artists = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            parseData(artists, jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return artists;
    }


    private void parseData(List<Artist> artists, String jsonString) {
        Gson gson = new GsonBuilder().create();

        Artist[] artistArr = gson.fromJson(jsonString, Artist[].class);

        for (Artist a : artistArr) {
            Artist artist = new Artist();
            artist.setId(a.getId());
            artist.setName(a.getName());
            artist.setGenres(a.getGenres());
            artist.setTracks(a.getTracks());
            artist.setAlbums(a.getAlbums());
            artist.setLink(a.getLink());
            artist.setDescription(a.getDescription());
            artist.setCover(a.getCover());
            artists.add(artist);
        }
    }

}
