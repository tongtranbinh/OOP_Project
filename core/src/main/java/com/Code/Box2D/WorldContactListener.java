package com.Code.Box2D;

import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Array;


public class WorldContactListener implements ContactListener {

    private boolean isPlayer;
    private boolean isCollision;
    public Array<Listener> listeners = new Array<Listener>();
    Main game;

    public WorldContactListener(Main game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity entity1 = (Entity) fixtureA.getUserData();
        Entity entity2 = (Entity) fixtureB.getUserData();

        listeners.add(new Listener(entity1,  entity2 ) );

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {


    }

    public void reset()
    {
        isPlayer = false;
        isCollision = false;
    }

    public Array<Listener> getListeners() {
        return listeners;
    }
}
