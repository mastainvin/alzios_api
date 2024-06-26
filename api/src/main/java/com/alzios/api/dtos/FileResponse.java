package com.alzios.api.dtos;

public class FileResponse {
    private String name;
    private String url;
    private String type;
    private Long size;
    public FileResponse(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getSize() {
        return size;
    }
    public void setSize(Long size){
        this.size = size;
    }
}
