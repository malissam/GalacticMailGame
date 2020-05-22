/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.GameObjects.*;



import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



import static javax.imageio.ImageIO.read;

/**
 * Main driver class of Tank Example.
 * Class is responsible for loading resources and
 * initializing game objects. Once completed, control will
 * be given to infinite loop which will act as our game loop.
 * A very simple game loop.
 * @author anthony-pc
 */
//JPANELL IS BLACK PART OF SCREEN
public class GameWorld extends JPanel {

    public static final int SCREEN_WIDTH = 1290;
    public static final int SCREEN_HEIGHT = 700;
    public BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame; //TITLEBAR
    private Ship ricksShip;
    private Background background;
    private ArrayList<GameObject> GameObjectsList;



    public static void main(String[] args) {
        GameWorld galacticGame = new GameWorld();
        int passScore = 0;
        galacticGame.init(1);
        for (int level = 1; level <= 3 && !galacticGame.ricksShip.scoreBoard.gameOver; level++) {
            boolean incompleteLevel = true;
            System.out.println(level);
            galacticGame.setLevel(level);
            galacticGame.ricksShip.scoreBoard.numberofPoints = passScore;
            try {
                while (incompleteLevel && !galacticGame.ricksShip.scoreBoard.gameOver) {
                    galacticGame.GameObjectsList.forEach(GameObjectList -> GameObjectList.update());
                    incompleteLevel = galacticGame.checkCollision();

                    if (galacticGame.ricksShip.scoreBoard.gameOver) {
                        System.out.println("Game over in while loop");
                        try {
                            galacticGame.ricksShip.scoreBoard.initials = galacticGame.ricksShip.scoreBoard.initials = (String) JOptionPane.showInputDialog(galacticGame, "You earned a spot on the score board\n"
                                    + "\"Enter your name Galactic Champion!, *no spaces please*\"", "High Score", 2);
                            if(galacticGame.ricksShip.scoreBoard.initials == ""){
                                galacticGame.ricksShip.scoreBoard.initials = "name";
                            }
                        }catch (Exception e){
                            galacticGame.ricksShip.scoreBoard.initials = "player unknown";
                        }
                    }
                    galacticGame.repaint();
                    Thread.sleep(1000 / 75);
                }
            } catch (InterruptedException ignored) {
                System.out.println(ignored);
            }
            passScore = galacticGame.ricksShip.scoreBoard.numberofPoints;
        }
        System.out.println("out of main for loop");

    }

    private void init(int level) {
        this.jFrame = new JFrame("Galactic Mail Game");
        this.world = new BufferedImage(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage backgroundImage = null;
        GameObjectsList = new ArrayList<>();

        try {
            //Using class loaders to read in resources
            backgroundImage = read(GameWorld.class.getClassLoader().getResource("Background.bmp"));

            background = new Background(SCREEN_WIDTH, SCREEN_HEIGHT - 50, backgroundImage);
            //location of tank at start
            ricksShip = new Ship(35, 35, 0, 0, 0, 1);

            setLevel(level);
            ShipControl shipControl = new ShipControl(ricksShip,
                    KeyEvent.VK_W,
                    KeyEvent.VK_S,
                    KeyEvent.VK_A,
                    KeyEvent.VK_D,
                    KeyEvent.VK_SPACE);


            this.jFrame.setLayout(new BorderLayout());
            this.jFrame.add(this);
            this.jFrame.addKeyListener(shipControl);
            this.jFrame.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT + 30);
            this.jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.jFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setLevel(int level) {
        try {
            InputStreamReader isr;
            if (level == 1) {
                isr = new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/GalacticLevelOne"));
            } else if (level == 2) {
                isr = new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/GalacticLevelTwo"));
            } else {
                isr = new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/GalacticMap"));
            }

            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("No data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
//////////////fix it: there is an issue with the bottom and right most border. Possibly a ratio issue between image size and world size
            for (int curRow = 0; curRow < numRows - 1; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols - 1; curCol++) {
                    switch (mapInfo[curCol]) {
                        ///i left this so that future maps could be easily implemented. maybe new moons, or other stationary objects like power ups.
                        case "1":
                            Asteroid straightAsteroid = new Asteroid(curCol * 50, curRow * 50, 10, 10, 0,1);
                            GameObjectsList.add(straightAsteroid);
                        case "2":
                            Asteroid angleRightAsteroid = new Asteroid(curCol * 50, curRow * 50, 10, 10, -45,1);
                            GameObjectsList.add(angleRightAsteroid);
                        case "3":
                            Asteroid angleLeftAsteroid = new Asteroid(curCol * 50, curRow * 50, 10, 10, 45,1);
                            GameObjectsList.add(angleLeftAsteroid);
                        case "4":
                            Asteroid backwardsAsteroid = new Asteroid(curCol * 50, curRow * 50, 1, 1, 90,1);
                            GameObjectsList.add(backwardsAsteroid);
                        case "5":
                            if(level == 3){
                                MailMoon moon = new MailMoon(curCol * 64, curRow * 63,2);
                                GameObjectsList.add(moon);}
                            else {
                                MailMoon moon = new MailMoon(curCol * 64, curRow * 63,0);
                                GameObjectsList.add(moon);
                            }
                        default:
                            break;
                    }
                }
            }


            GameObjectsList.add(ricksShip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public boolean checkCollision() {
/////TODO: make the repetitive parts another method that gets passed the parts that change.
        //goes through each index in the gameobject arraylist
        int moonCheck = 0;
        for (int i = 0; i < GameObjectsList.size(); i++) {
            if(GameObjectsList.get(i) instanceof MailMoon){
                moonCheck +=1;
            }
            //checks if that objects active attribute is true. object on the map are active
            if (GameObjectsList.get(i).isActive()) {
                ///if the tank one intersects an object()
                if (i != GameObjectsList.size() - 1 && (ricksShip.getRectangle().contains(GameObjectsList.get(i).getX()+10,GameObjectsList.get(i).getY()+10) ||
                ricksShip.getRectangle().contains(GameObjectsList.get(i).getX()+30,GameObjectsList.get(i).getY()+30))){
                    if (GameObjectsList.get(i) instanceof Asteroid) {
                        ricksShip.collision(GameObjectsList.get(i)); //what it collided with
                        GameObjectsList.get(i).collision(ricksShip);
                    }
                    if (GameObjectsList.get(i) instanceof MailMoon) {
                        ricksShip.collision(GameObjectsList.get(i)); //what it collided with
                    }
                }
            }
            else{
                GameObjectsList.remove(i);
            }
        }
        if(moonCheck == 0){
           return false;
        }
        return true;
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();

        this.background.drawImage(buffer);
        this.ricksShip.drawImage(buffer);
        for (int i = 0; i < this.GameObjectsList.size(); i++) {
            this.GameObjectsList.get(i).drawImage(this.buffer);
        }
        g2.fillRect(0, 0, 2000, 2000);

        g2.drawImage(world, 0, 0, null);
        ricksShip.scoreBoard.drawImage(g2);
    }


}
