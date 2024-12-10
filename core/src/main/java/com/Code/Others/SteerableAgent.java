package com.Code.Others;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SteerableAgent implements Steerable<Vector2> {
    Body body;
    Boolean tagged;
    float boundingRadius;
    float maxLinearSpeed;
    float maxAngularSpeed;
    float maxAngularAcceleration;
    float maxLinearAcceleration;

    public SteerableAgent(Body body, float boundingRadius) {
        this.body = body;
        this.boundingRadius = boundingRadius;
        this.maxLinearSpeed = 2000.0F;
        this.maxLinearAcceleration = 500.0F;
        this.maxAngularSpeed = 120.0F;
        this.maxAngularAcceleration = 20.0F;
    }

    public Vector2 getLinearVelocity() {
        return this.body.getLinearVelocity();
    }

    public float getAngularVelocity() {
        return this.body.getAngularVelocity();
    }

    public float getBoundingRadius() {
        return this.boundingRadius;
    }

    public boolean isTagged() {
        return this.tagged;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public float getZeroLinearSpeedThreshold() {
        return 0.0F;
    }

    public void setZeroLinearSpeedThreshold(float v) {
    }

    public float getMaxLinearSpeed() {
        return this.maxLinearSpeed;
    }

    public void setMaxLinearSpeed(float v) {
        this.maxLinearSpeed = v;
    }

    public float getMaxLinearAcceleration() {
        return this.maxLinearAcceleration;
    }

    public void setMaxLinearAcceleration(float v) {
        this.maxLinearAcceleration = v;
    }

    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    public void setMaxAngularSpeed(float v) {
        this.maxAngularSpeed = v;
    }

    public float getMaxAngularAcceleration() {
        return this.getMaxLinearAcceleration();
    }

    public void setMaxAngularAcceleration(float v) {
        this.maxAngularAcceleration = v;
    }

    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    public float getOrientation() {
        return this.body.getAngle();
    }

    public void setOrientation(float v) {
        this.body.setTransform(this.getPosition(), v);
    }

    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2((double)(-vector.x), (double)vector.y);
    }

    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -((float)Math.sin((double)angle));
        outVector.y = (float)Math.cos((double)angle);
        return outVector;
    }

    public Location<Vector2> newLocation() {
        return new Location<Vector2>() {
            private Vector2 position = new Vector2();

            public Vector2 getPosition() {
                return this.position;
            }

            public float getOrientation() {
                return 0.0F;
            }

            public void setOrientation(float orientation) {
            }

            public Location<Vector2> newLocation() {
                return this;
            }

            public float vectorToAngle(Vector2 vector) {
                return (float)Math.atan2((double)(-vector.x), (double)vector.y);
            }

            public Vector2 angleToVector(Vector2 outVector, float angle) {
                outVector.x = -((float)Math.sin((double)angle));
                outVector.y = (float)Math.cos((double)angle);
                return outVector;
            }
        };
    }
}
