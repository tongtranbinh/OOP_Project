package com.Code.Box2D;

import com.badlogic.gdx.physics.box2d.*;

public class InteractTileObject {
    public BodyDef bodyDef = new BodyDef();
    public PolygonShape polygonShape = new PolygonShape();
    public CircleShape circleShape = new CircleShape();
    public FixtureDef fixtureDef = new FixtureDef();
    public Body body;
    public float objectX, objectY;
}
