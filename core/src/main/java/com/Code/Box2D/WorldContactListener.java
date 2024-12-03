package com.Code.Box2D;

import com.Code.Main;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {

    private boolean isPlayer;
    private boolean isCollision;
    Main game;

    public WorldContactListener(Main game)
    {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println(fixtureA.getBody().getUserData() + " hit " + fixtureB.getBody().getUserData());

        if(fixtureA.getBody().getUserData() == "Player" || fixtureB.getBody().getUserData() == "Player"){
            isPlayer = true;
        }


        if(fixtureA.getBody().getUserData() == "Collision" || fixtureB.getBody().getUserData() == "Collision"){
            isCollision = true;
        }




    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("End");
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


}
