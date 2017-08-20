package GameLogic;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Submarino {

    private int moveRight, moveLeft, x, y, Right;
    private int spd = 9, anchoimg = 110, altoimg = 80;
    PartidaCanvas cnvs;
    private Image submarineImg, submarineImgR;

    public Submarino(int x, int posy, PartidaCanvas c) {
        this.x = x;
        this.y = posy;
        this.cnvs = c;
        moveRight = 0;
        moveLeft = 0;
        submarineImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/submarino.gif"));
        submarineImgR = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/submarinor.png"));
    }

    public void move() {
        if (moveRight == 1) {
            if (x < cnvs.getWidth() - getWidth_Imgsub() - 3) {
                x += spd;
            }
            Right = 1;
        }
        if (moveLeft == 1) {
            if (x > 3) {
                x -= spd;
            }
            Right = 0;
        }
        cnvs.repaint(x - 15, y - 5, getWidth_Imgsub() + 30, getHeight_Imgsub() + 10);
//        cnvs.repaint();
    }

    public void Move_Right(int moveRight) {
        this.moveRight = moveRight;
    }

    public void Move_Left(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
    public void setSpdmax(int spd) {
        this.spd += spd;
        if (spd>16) {
            this.spd=16;
        }
    }
    public void restar_spd(){
      this.spd =  9;
    }

    // geters                      ++++++++++++geters++++++++++

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getSubmarineImg() {
        if (Right == 1) {
            return submarineImgR;
        }else {
            return submarineImg;}
        
    }

    public int getWidth_Imgsub() {
        return anchoimg;
    }

    public int getHeight_Imgsub() {
        return altoimg;
    }

    public int getMoveRight() {
        return moveRight;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth_Imgsub(), (getHeight_Imgsub()));

    }
}
