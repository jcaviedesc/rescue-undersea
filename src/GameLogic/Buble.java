package GameLogic;

//import static canastapoedos.Controlador.add_score;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

public class Buble extends Thread {

    protected int x, y, ya = 0, desplasamiento = 50, id = 0;
    double xa = 3.0;
    protected Image bubleImg;
    protected PartidaCanvas paneldraw;
    protected Submarino submarine;
    protected Controlador brain;
    protected boolean pararHilo = true;

    Buble(Controlador autoridad, int pos_x, int pos_y, int i) {
        x = pos_x;
        y = pos_y;
        id = i;
        ya = -3;
        brain = autoridad;
    }

    void setSubmarino_dfs(Submarino def) {
        submarine = def;
    }

    public void setCanvas(PartidaCanvas game) {
        paneldraw = game;
    }

    void moveglobo() throws InterruptedException, LineUnavailableException {
        // si los globos no son rebentados o collisionan con el submarino
        if (y <= -60) {
            brain.controldevidas(id);
            reutilizar_globe();
        } else if (collision()) {
            brain.add_score(id);
            paneldraw.repaint(0, 0, paneldraw.getWidth(), areaRepaintimg());
            reutilizar_globe();
        }
        movimiento(y,20,12);
        y -= (int) (Math.random() * 2) + 3;
        paneldraw.repaint(x - 10, y - 10, getWidth_bubleImg() + 10, getHeight_bubleImg() + 10);
        Thread.sleep(desplasamiento);
    }

    public void reutilizar_globe() {
        y = ((int) (Math.random() * 265) +(paneldraw.getHeight()+ 80));
        x = (int) (Math.random() * (paneldraw.getWidth() - (getWidth_bubleImg() - 1))) + 1;
    }

    public void movimiento(int cord,int right, int left) {
        double d = Math.toRadians(cord);
        if (x > paneldraw.getWidth() - getWidth_bubleImg()+2)
            x -= right;
        else if (x < 8){
            x += left;
        }
        if (getId()%2==0) xa = Math.sin(d) + (Math.sin(d) * ((Math.random() * 1.8) + 2.6));
        else xa = Math.cos(d) + (Math.cos(d) * ((Math.random() * 1.8) + 2.6)); 
        x += xa;
    }

    public void cambiarVel(int vel) {
        desplasamiento -= vel;
    }

    void cargarimagenPNG_random(String rta) {
        int a = (int) (Math.random() * 6) + 1;
        bubleImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/" + rta + a + ".png"));
    }

    public void cargarImgGIF(Image la_image) {
        bubleImg = la_image;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setYrestar(int y) {
        this.y -= y;
    }

    public void setXmasxa(int x) {
        this.x += x;
    }

    public void setXmenos(int x) {
        this.x -= x;
    }
//geters

    public Submarino getSubmarine() {
        return submarine;
    }

    public PartidaCanvas getPaneldraw() {
        return paneldraw;
    }

    public Image getBubleImg() {
        return bubleImg;
    }

    public int areaRepaintimg() {
        int areapaint = getSubmarine().getY() + getSubmarine().getHeight_Imgsub() + bubleImg.getHeight(paneldraw) + 15;
        return areapaint;
    }

    public int getWidth_bubleImg() {
        return bubleImg.getWidth(paneldraw);
    }

    public int getHeight_bubleImg() {
        return bubleImg.getHeight(paneldraw);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 50);
    }

    public boolean collision() {
        return getSubmarine().getBounds().intersects(getBounds()); // choque con el submarino
    }
//metode thread 

    public void run() {
        while (pararHilo) {
            try {
                moveglobo();
            } catch (InterruptedException | LineUnavailableException ex) {
                //ex.printStackTrace();
                Logger.getLogger(Buble.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("ideee  " + id + "murio");
        stop();
    }

    public void setPararHilo(boolean state) {
        pararHilo = state;
    }
}
