package com.Code.Entity.System;

import com.Code.Box2D.Listener;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;


public class CollisionSystem extends IteratingSystem {

    Main game;
    Array<Listener> listeners;

    public CollisionSystem(Main game) {
        super(Family.all().get());
        this.game = game;
        listeners = game.worldContactListener.getListeners();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){

    }
}
