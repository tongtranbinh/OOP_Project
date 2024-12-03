package com.Code.Map;

public enum MapType {
    STARTING ("laboratory/Starting.tmx");


    private final String filepath;

    MapType(final String filepath)
    {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }
}
