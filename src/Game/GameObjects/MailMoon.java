package Game.GameObjects;

import Game.GameWorld;

import java.awt.*;
import java.io.IOException;

import static javax.imageio.ImageIO.read;


public class MailMoon extends GameObject {

    private Rectangle rectangle;
    private int r =0;
    public MailMoon(int x, int y,int r) {

        setX(x);
        setY(y);
        setR(r);
        try {
            this.image = read(MailMoon.class.getClassLoader().getResource("Moon1.png" ));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.rectangle = setRectangle(this.x,this.y,this.image.getWidth(),this.image.getHeight());
        this.active = true;
    }




    @Override
    public void collision(GameObject objectColliding) {
       this.active = false;
    }
}
