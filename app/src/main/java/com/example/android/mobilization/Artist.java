package com.example.android.mobilization;

import java.util.ArrayList;
import java.util.List;

public class Artist {

    /**
     * id : 1080505
     * name : Tove Lo
     * genres : ["pop","dance","electronics"]
     * tracks : 81
     * albums : 22
     * link : http://www.tove-lo.com/
     * description :
     * cover : {"small":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300","big":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"}
     */

    private int id;
    private String name;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    /**
     * small : http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300
     * big : http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000
     */

    private ArrayList<String> genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
