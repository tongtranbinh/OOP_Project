package com.Code.Entity.System;

import com.Code.Effect.DamageArea;
import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class BossAttackSystem extends IteratingSystem {

    Main game;
    Vector2 position;
    Vector2 direction;
    float speed, time;
    int damage;
    float[][] dirx = {{-1, -0.7f, 0, 0.7f, 1, 0.7f, 0, -0.7f},{-0.92f, -0.38f, 0.38f, 0.92f, 0.92f, 0.38f, -0.38f, -0.92f}};
    float[][] diry = {{0, 0.7f, 1, 0.7f, 0, -0.7f, -1, -0.7f},{0.38f, 0.92f, 0.92f, 0.38f, -0.38f, -0.92f, -0.92f, -0.38f}};

    float[] dir = {-300, -200, -100, 0 , 100 , 200, 300};
    public BossAttackSystem(Main  game) {
        super(Family.all(BossComponent.class, Box2DComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BossComponent bossComponent = ECSEngine.bossComponentMapper.get(entity);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
        position = box2DComponent.body.getPosition();
        bossComponent.reloadtime += deltaTime;
        bossComponent.reloadSkill += deltaTime;
        if(bossComponent.reloadtime > 3f && bossComponent.readytoAttack && !bossComponent.stop){
            bossComponent.reloadtime = 0;
            for (int i = 0; i <= 7; i++){
                direction = new Vector2(dirx[0][i], diry[0][i]);
                game.ecsEngine.createDamageArea(new DamageArea(position, direction,
                    game.BaseSize, game.BaseSize, 10, 200 * Main.PPM, 5f, true));
            }
        }
        if(bossComponent.reloadSkill > 5f && bossComponent.readytoAttack){
            bossComponent.stop = true;
        }

        if(bossComponent.stop && bossComponent.readytoAttack){
            if(bossComponent.Skill1){
                bossComponent.timeSkill1 += deltaTime;
                bossComponent.timeCntSkill1 += deltaTime;
                if(bossComponent.timeCntSkill1 >= 0.6f){
                    bossComponent.cntSkill1 = (bossComponent.cntSkill1 + 1) % 2;
                    int t = bossComponent.cntSkill1;
                    for (int i = 0; i <= 7; i++){
                        direction = new Vector2(dirx[t][i], diry[t][i]);
                        game.ecsEngine.createDamageArea(new DamageArea(position, direction,
                            game.BaseSize, game.BaseSize, 10, 200 * Main.PPM, 5f, true));
                    }
                    bossComponent.timeCntSkill1 = 0;
                }

                if(bossComponent.timeSkill1 > 3f){

                    bossComponent.stop = false;
                    bossComponent.Skill1 = false;
                    bossComponent.Skill2 = true;
                    bossComponent.reloadtime = 0;
                    bossComponent.timeCntSkill1 = 0;
                    bossComponent.cntSkill1 = 0;
                    bossComponent.reloadSkill = 0;
                    bossComponent.timeSkill1 = 0;
                }
            }
            else{
                bossComponent.timeSkill1 += deltaTime;
                bossComponent.timeCntSkill1 += deltaTime;
                if(bossComponent.timeCntSkill1 >= 0.6f){
                    direction = new Vector2(0,0);
                    direction.x += (bossComponent.direction == DirectionType.RIGHT) ? 1 : 0;
                    direction.x -= (bossComponent.direction == DirectionType.LEFT) ? 1 : 0;
                    direction.y -= (bossComponent.direction == DirectionType.DOWN) ? 1 : 0;
                    direction.y += (bossComponent.direction == DirectionType.UP) ? 1 : 0;

                    for (int i = 0; i < 7; i++){
                        float posx = position.x;
                        float posy = position.y;

                        if((bossComponent.direction == DirectionType.RIGHT) ||
                            (bossComponent.direction == DirectionType.LEFT)) posy += (dir[i] * Main.PPM);
                        if((bossComponent.direction == DirectionType.UP) ||
                            (bossComponent.direction == DirectionType.DOWN)) posx += (dir[i] * Main.PPM);
                        game.ecsEngine.createDamageArea(new DamageArea(new Vector2(posx, posy), direction,
                            game.BaseSize, game.BaseSize, 10, 200 * Main.PPM, 5f, true));
                    }
                    System.out.println("cc");
                    bossComponent.timeCntSkill1 = 0;
                }

                if(bossComponent.timeSkill1 > 3f){

                    bossComponent.stop = false;
                    bossComponent.Skill1 = true;
                    bossComponent.Skill2 = false;
                    bossComponent.reloadtime = 0;
                    bossComponent.timeCntSkill1 = 0;
                    bossComponent.cntSkill1 = 0;
                    bossComponent.reloadSkill = 0;
                    bossComponent.timeSkill1 = 0;
                }

            }
        }
    }

}
