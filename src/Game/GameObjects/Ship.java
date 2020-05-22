package Game.GameObjects;

import Game.GameWorld;
import Game.ScoreBoard;

import java.awt.*;
import java.awt.image.BufferedImage;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author  Malissa Murga
 */
public class Ship extends GameObject {

    private final double R = 1;
    private double ROTATION_SPEED = 1;
    private double lastBullet =0;
    private double shieldStart =0;
    private BufferedImage tankImage;
    //private BufferedImage tankImage2;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean onMoon = false;
    private int shieldHealth = 3;
    private Rectangle lastPosition;
    private int lifeLeft = 3;
    ///public int health;
    public ScoreBoard scoreBoard;
  //  public ArrayList<Bullet> bulletBarrage;


    public Ship(int x, int y, int vx, int vy, int angle, int playerCount)  {
        this.setX(x);
        this.setY(y);
        this.setVx(vx);
        this.setVy(vy);
        this.setAngle(angle);
        this.isActive();
        try {
            if (playerCount == 1) {
               tankImage = read(Ship.class.getClassLoader().getResource("rickship1.png"));
               this.image = tankImage;
            }

        }catch(Exception e){System.out.print(e);}

 //       this.bulletBarrage = new ArrayList<>();
        this.rectangle = setRectangle(this.x,this.y,this.image.getWidth(),this.image.getHeight());
       // this.health = 700;
        this.scoreBoard = new ScoreBoard();
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }
    public void toggleDownPressed() {
        this.DownPressed = true;
    }
    public void toggleRightPressed() {
        this.RightPressed = true;
    }
    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    public void toggleShootPressed() {
        this.ShootPressed = true;
    }
    public void unToggleUpPressed() {
        this.UpPressed = false;
    }
    public void unToggleDownPressed() {
        this.DownPressed = false;
    }
    public void unToggleRightPressed() {
        this.RightPressed = false;
    }
    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    public void update() {
        lastPosition = getRectangle();

     //   if (this.UpPressed) {
            this.moveForwards();
       // }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

    /*    if (this.ShootPressed && (System.currentTimeMillis()-lastBullet)>500) {

           // System.out.println("shots fired");
        }*/
        //for each bullet do this...

        //this.bulletBarrage.forEach(bullet -> bullet.update());

    }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }
    private void moveBackwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }
    @Override
    public void moveForwards() {
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x += vx;
            y += vy;
            checkBorder();
    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void collision(GameObject objectColliding) {
        if (objectColliding instanceof Asteroid) {
            if (onMoon == false) {
                this.scoreBoard.gameOver = true;
                this.RightPressed = false;
                this.LeftPressed = false;
            }
        }
        if (objectColliding instanceof MailMoon) {
            if (!this.scoreBoard.gameOver) {
                this.onMoon = true;
                this.ROTATION_SPEED = 2;
                this.setX(objectColliding.x);
                this.setY(objectColliding.y);
                if ((System.currentTimeMillis() - scoreBoard.lastPointLoss) > 200) {
                    scoreBoard.numberofPoints = scoreBoard.numberofPoints - 1;
                    scoreBoard.lastPointLoss = System.currentTimeMillis();
                }
                if (this.ShootPressed) {
                    objectColliding.collision(objectColliding);
                    this.ROTATION_SPEED = 1;
                    scoreBoard.setPoints();
                    this.onMoon = false;
                }

            }
        }
    }






}
