package com.Code.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

public class Maploader {
    public ArrayList<TiledMap> map = new ArrayList<TiledMap>();
    public TmxMapLoader mapLoader = new TmxMapLoader();
    TiledMap addMap = new TiledMap();
    public Maploader()
    {
        addMap = mapLoader.load("laboratory/Starting.tmx");

        map.add(addMap);
    }


    public TiledMap CreateMap(){

        return map.get(0);
    }
}
