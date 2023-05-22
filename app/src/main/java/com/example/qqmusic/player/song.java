package com.example.qqmusic.player;

public class song {
    private String fileName;
    private String singer;
    private String album;
    private String size;
    private String filePath;
    private String title;
    private int duration;
    public song(String fileName,String title,int duration,String singer,String album,String size,String filePath){
        this.fileName = fileName;
        this.title = title;
        this.duration = duration;
        this.singer = singer;
        this.album = album;
        this.size = size;
        this.filePath = filePath;

    }
    public void setFileName(String fileName){this.fileName = fileName;}
    public void setTitle(String title){ this.title = title;}
    public void setDuration(int duration){ this.duration=duration;}
    public void setSinger(String singer){ this.singer = singer;}
    public void setAlbum(String album) { this.album = album;}
    public void setSize(String size){ this.size = size;}
    public void setFilePath(String filePath){this.filePath = filePath;}
    public String getFileName(){ return fileName;}
    public String getTitle(){ return title;}
    public int getDuration(){ return duration;}
    public String getSinger(){return singer;}
    public String getAlbum() { return album;}
    public String getSize() {return size;}
    public String getFilePath() {return filePath;}
}
