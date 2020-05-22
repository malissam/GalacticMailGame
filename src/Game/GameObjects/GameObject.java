package Game.GameObjects;

import Game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject  {
    int x;
    int y;
    int vx;
    int vy;
    int angle;
    int height;
    int width;
    int R= 1;
    boolean active = true;
    Rectangle rectangle;
    BufferedImage image;

    //public Rectangle rectangle;
    public GameObject(){}

    public GameObject(int x,int y){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        height = image.getHeight();
        width = image.getWidth();
    }
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth()/2.0 , this.image.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
       // g2d.setColor(Color.green);
        g2d.drawImage(this.image, rotation, null);
       // g2d.drawRect(x,y, this.image.getWidth(), this.image.getHeight());
    }
    public boolean isActive() {
        return this.active;
    }

    void setX(int x_to_set) {
        this.x = x_to_set;
    }

    public int getX() {
        return this.x;
    }

    void setY(int y_to_set) {
        this.y = y_to_set;
    }

    public int getY() {
        return this.y;
    }

    int getVx() {
        return vx;
    }

    void setVx(int vx) {
        this.vx = vx;
    }

    int getVy() {
        return vy;
    }

    void setVy(int vy) {
        this.vy = vy;
    }

    int getAngle() {
        return angle;
    }

    void setAngle(int angle) {
        this.angle = angle;
    }

    int getR() {
        return vx;
    }

    void setR(int r) {
        this.R = r;
    }
    public void checkBorder() {
        if (x <= 0) x = GameWorld.SCREEN_WIDTH;
        if (x >= GameWorld.SCREEN_WIDTH) x = 0;
        if (y < 0) y = GameWorld.SCREEN_HEIGHT-30;
        if (y > GameWorld.SCREEN_HEIGHT) y = 0;
    }
    public void moveForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public Rectangle setRectangle(int x,int y,int width,int height){
        return this.rectangle = new Rectangle( x, y,width,height);
    }

    public Rectangle getRectangle(){
        return new Rectangle( x, y,this.image.getWidth()-6,this.image.getHeight()-6);

    }

    public  void update(){
            moveForwards();
    }


    public abstract void collision(GameObject obj);

}
