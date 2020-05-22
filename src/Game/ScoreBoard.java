package Game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.Arrays;



import static javax.imageio.ImageIO.read;

public class ScoreBoard {
    public int numberofPoints;
    public long lastPointLoss = System.currentTimeMillis();
    private TreeMap<Integer,String> scoreMap;
    private int scoreArray[];
    private int myScore;
    public boolean gameOver = false;
    public String initials = "mm";
    private int timesAfterGameOver=0;
    public ScoreBoard(){
        this.numberofPoints = 25;
        this.scoreMap = new TreeMap<>();
        this.scoreArray = new int[10];

    }
    public void setPoints(){
        this.numberofPoints = this.numberofPoints + 50;
    }




    private void readBoard(){
    // private int[] readBoard(){

        try{
            File myScoreTable = new File("scoreTable");
            if(myScoreTable.createNewFile()){
                FileWriter myWriter = new FileWriter("scoreTable");
                myWriter.write("malissa 1 malissa 2 malissa 3 malissa 4 malissa 5 malissa 6 malissa 7 malissa 8 xx 9 xx 10 ");
              //  myWriter.write("0 0 0 0 0 0 0 0 0 0 ");
                myWriter.close();
            }
            Scanner myReader = new Scanner(myScoreTable);

                String row = myReader.nextLine();
                System.out.println("read line_____________________________");
                myReader.close();
            System.out.println(row);
            String[] scoreInfo = row.split(" ");
            System.out.println(scoreInfo.length);
            for (int i= 0; i<scoreInfo.length-1;i=i+2) {
                System.out.println(scoreInfo[i]);
                System.out.println(scoreInfo[i+1]);
                System.out.println(scoreMap.entrySet());
                scoreMap.put(Integer.parseInt(scoreInfo[i+1]), scoreInfo[i]);
               // scoreArray[i] = Integer.parseInt(scoreInfo[i]);
                // System.out.println("string to number array in read board ");
            //    System.out.print( scoreArray[i] + " ");
            }

          //  System.out.println();
          //  Arrays.sort(scoreArray);
            // this.highScore = scoreArray[0];
          //  return scoreArray;
          //  return scoreMap;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
     //   return null;
    }
     /*       for (int i= 0; i<scoreInfo.length; i=+2) {
                System.out.println(scoreInfo[i]);
                scoreMap.put(Integer.parseInt(scoreInfo[i+1]),scoreInfo[i]);
            }
            System.out.println(scoreMap.entrySet());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }*/
    private void saveScore( TreeMap<Integer,String> scoreMap){

    //private void saveScore( int[] scoreArray){

        System.out.println("in savescore func: ");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("scoreTable");
            String scoresToString = "";
            int score;
            String name;

            for (Map.Entry<Integer, String> entry : scoreMap.entrySet()){
                score = entry.getKey();
                name = entry.getValue();
                scoresToString += name + " " + score + " ";
            }
            System.out.println(scoreMap.entrySet());
            //for(int i=0;i<scoreMap.size(); i++){

              //  System.out.print(scoreArray[i] + " ");
              //  System.out.println();
              //  scoresToString += scoreArray[i] + " " ;
          //  }
          /*  for(int i=0;i<scoreMap.size(); i++){
              //  System.out.print(scoreArray[i] + " ");
            //    System.out.println();
            //    scoresToString += scoreMap.get() + " " ;
                for (Map.Entry<Integer, String> entry : scoreMap.entrySet()) {
                    int key = entry.getKey();
                    String value = entry.getValue();
                    scoresToString += value + key + " " ;
                    System.out.printf("%s : %s\n", key, value);
                }
            }*/
            fileWriter.write(scoresToString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void drawImage(Graphics g) {
        Font myFont = new Font("Courier New", 1, 30);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(myFont);
        g2d.drawString("Mission Earnings $" + this.numberofPoints, GameWorld.SCREEN_WIDTH / 2, 35);
        System.out.println("gameover in draw scoreboard   " + this.gameOver);
        TextField textField = new TextField();
        textField.setBackground(Color.black);
        textField.setBounds(GameWorld.SCREEN_WIDTH/2,GameWorld.SCREEN_HEIGHT/2,50,15);
        textField.isVisible();

        if (this.gameOver) {
            readBoard();

            if(this.numberofPoints > scoreMap.firstKey()){
                scoreMap.remove(scoreMap.firstKey());

                scoreMap.put(this.numberofPoints, initials);
                saveScore(scoreMap);
            }
            System.out.println(scoreMap.entrySet());
/*            System.out.println();
            for (int i = 0; i < scoreArray.length; i++) {
                System.out.print(scoreArray[i] + " ");
            }
            System.out.println();

            if (scoreArray[0] < this.numberofPoints && timesAfterGameOver == 0) {
                scoreArray[0] = this.numberofPoints;
                Arrays.sort(this.scoreArray);
                saveScore(this.scoreArray);
                System.out.print("score array if number of points is greater");
                for (int i = 0; i < scoreArray.length; i++) {
                    System.out.print(scoreArray[i] + " ");
                }
                System.out.println();
            }*/
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT);
            g2d.setColor(Color.red);
            g2d.drawString("Game Over Galactic Mailman ", GameWorld.SCREEN_WIDTH / 2 - 150, GameWorld.SCREEN_HEIGHT / 2 - 300);
            g2d.drawString("Top Ten Scores", GameWorld.SCREEN_WIDTH / 2 - 155, GameWorld.SCREEN_HEIGHT / 2 - 200);

            int spaceBetweenPrintedEntries = 0;
            Map<Integer, String> descendingMap = scoreMap.descendingMap();
            for (Map.Entry<Integer, String> entry : descendingMap.entrySet()){
                g2d.drawString(entry.getKey() + "  " + entry.getValue(), GameWorld.SCREEN_WIDTH / 2 - 90, GameWorld.SCREEN_HEIGHT / 3 + 30*spaceBetweenPrintedEntries );
                spaceBetweenPrintedEntries +=1;
            }
    /*        for (int i = 0; i < scoreArray.length; i++) {
                if (scoreArray[i] == this.numberofPoints) {
                    g2d.drawString(initials + "  " + scoreArray[i], GameWorld.SCREEN_WIDTH / 2 - 90, GameWorld.SCREEN_HEIGHT / 3 + 30 * i);
                } else {
                    g2d.drawString("" + scoreArray[i], GameWorld.SCREEN_WIDTH / 2, GameWorld.SCREEN_HEIGHT / 3 + 30 * i);

                    for (Map.Entry<Integer, String> entry : scoreMap.entrySet()) {
                        g2d.drawString(entry.getValue() + " " + entry.getKey(), GameWorld.SCREEN_WIDTH / 2, GameWorld.SCREEN_HEIGHT / 3 + 30 * spaceBetweenPrintedEntries);
                        spaceBetweenPrintedEntries = +1;
                    }
                }
                timesAfterGameOver += 1;
            }*/
        }
    }
    }

