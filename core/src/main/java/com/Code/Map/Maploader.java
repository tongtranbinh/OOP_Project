package com.Code.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Maploader {
    public TiledMap map = new TiledMap();
    public TmxMapLoader mapLoader = new TmxMapLoader();

    public TiledMap CreateMap(){

        map = mapLoader.load("The_Forest_Of_Dream/The_Forest_Of_Dream.tmx");
        return map;
    }
}