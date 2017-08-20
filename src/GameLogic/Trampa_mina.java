/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

public class Trampa_mina extends Buble {

    public Trampa_mina(Controlador autoridad, PartidaCanvas canvas, Submarino subma) {
        super(autoridad, canvas.getWidth() - 200, canvas.getHeight() + 300, 12);
        super.setCanvas(canvas);
        super.setSubmarino_dfs(subma);
    }

    public void moveMina() throws InterruptedException, LineUnavailableException {
        // si los globos no son rebentados o collisionan con la defenza
        if (super.getY() < -60) {
            super.reutilizar_globe();
            Estrellavida.mi_sleep(4000);
        } else if (super.collision()) {
            brain.restarVidas();
            super.reutilizar_globe();
            super.getPaneldraw().repaint(0, 0, super.getPaneldraw().getWidth(), super.areaRepaintimg());
            Estrellavida.mi_sleep(6000);
        }
        super.movimiento(super.getY(),15,15);
        super.setYrestar(5);
        super.getPaneldraw().repaint(super.getX() - 5, super.getY() - 5, getWidth_bubleImg() + 10, getHeight_bubleImg() + 10);
        Thread.sleep(60);
    }

    public void run() {
        while (true) {
            try {
                moveMina();
            } catch (InterruptedException | LineUnavailableException ex) {
                Logger.getLogger(Estrellavida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reinicio() {
        super.y = super.getPaneldraw().getHeight() + 60;
        resume();
    }

    public void paused() {
        //super.y = super.getPaneldraw().getHeight() + 15;
        suspend();
    }

    public void detener() {
        System.out.println("mina detenida");
        stop();
    }
}
