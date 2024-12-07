package com.Code.Entity.Component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationComponent implements Component {
    public ParticleEffect particleEffect;
    public TextureAtlas atlas;
}
