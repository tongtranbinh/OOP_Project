package com.Code.Box2D;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;


public class Box2Dobject {

    BodyDef bodyDef = new BodyDef(); // cung cap thuoc tinh
    PolygonShape shape = new PolygonShape();//cung cap hinh dang
    FixtureDef fixtureDef = new FixtureDef();// cung cap thuoc tinh
    Body body;
    TiledMap map;
    World world;

    public Box2Dobject(TiledMap map,World world)
    {
        this.map = map;
        this.world = world;
    }

    public void getObject(){
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth()/2,rectangle.getY() + rectangle.getHeight()/2);
            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);

        }
    }
}
