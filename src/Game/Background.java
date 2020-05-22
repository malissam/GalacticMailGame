package Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Background {
    private int screenWidth;
    private int screenHeight;

    private BufferedImage backgroundImage;

    Background(int width, int height, BufferedImage backgroundImage) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.backgroundImage = backgroundImage;

    }

    void drawImage(Graphics g) {

                AffineTransform position = AffineTransform.getTranslateInstance(0, 0);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(this.backgroundImage, position, null);
    }
}

