package Game.GameObjects;

import Game.GameWorld;

import java.awt.*;
import java.io.IOException;

import static javax.imageio.ImageIO.read;


public class Asteroid extends GameObject {
    private Rectangle rectangle;
    private final double R = 1;
    public Asteroid(int x, int y,int vx, int vy, int angle, int r) {
        this.setX(x);
        this.setY(y);
        this.setVx(vx);
        this.setVy(vy);
        this.setR(r);
        this.setAngle(angle);
        this.active = true;

        try {
                this.image = read(Asteroid.class.getClassLoader().getResource("asteroidL.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         this.rectangle = setRectangle(x,y,this.image.getWidth(),this.image.getHeight());
    }

    @Override
    public void collision(GameObject objectColliding) {
    }
}
