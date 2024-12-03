package com.Code.Controller;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KeyHandler implements InputProcessor {

    public int direction = 2;
    public boolean KeyProcessed;
    public boolean up, left, down, right, isAttack1, isAttack2;

    @Override
    public boolean keyDown(int keycode) {
        KeyProcessed = false;
        switch (keycode){
            case Input.Keys.W:{
                KeyProcessed = true;
                up = true;
                direction = 0;
                break;
            }
            case Input.Keys.A:{
                KeyProcessed = true;
                direction = 1;
                left = true;
                break;
            }
            case Input.Keys.S:{
                KeyProcessed = true;
                direction = 2;
                down = true;
                break;
            }
            case Input.Keys.D:{
                KeyProcessed = true;
                direction = 3;
                right = true;
                break;
            }
            case Input.Keys.J:{
                KeyProcessed = true;
                isAttack1 = true;
                break;
            }
            case Input.Keys.K:{
                KeyProcessed = true;
                isAttack2 = true;
                break;
            }
        }
        return KeyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.W:{
                KeyProcessed = true;
                direction = 0;
                up = false;
                break;
            }
            case Input.Keys.A:{
                KeyProcessed = true;
                direction = 1;
                left = false;
                break;
            }
            case Input.Keys.S:{
                KeyProcessed = true;
                direction = 2;
                down = false;
                break;
            }
            case Input.Keys.D:{
                KeyProcessed = true;
                direction = 3;
                right = false;
                break;
            }
            case Input.Keys.J:{
                KeyProcessed = true;
                isAttack1 = false;
                break;
            }
            case Input.Keys.K:{
                KeyProcessed = true;
                isAttack2 = false;
                break;
            }
        }
        return KeyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
