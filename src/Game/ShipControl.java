/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;


import Game.GameObjects.Ship;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author  murgatroid
 */
public class ShipControl implements KeyListener {

    private Ship tank;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    
    public ShipControl(Ship ship, int up, int down, int left, int right, int shoot) {
        this.tank = ship;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //PRESS AND RESEASE
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.tank.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.tank.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.tank.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.tank.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.tank.toggleShootPressed();
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.tank.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.tank.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.tank.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.tank.unToggleRightPressed();
        }
        if (keyReleased == shoot) {
            this.tank.unToggleShootPressed();
        }
    }
}
