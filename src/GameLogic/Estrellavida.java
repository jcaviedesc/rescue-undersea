/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

public class Estrellavida extends Buble {

    public Estrellavida(Controlador cerebro, PartidaCanvas canvas, Submarino subma) {
        super(cerebro, canvas.getWidth() / 2, canvas.getHeight() + 20, 10);
        this.brain = cerebro;
        super.setCanvas(canvas);
        super.setSubmarino_dfs(subma);
    }

    public void moverestrella() throws InterruptedException, LineUnavailableException {
        // si la estrella no es rebentada o collisionan con el submarino
        if (super.getY() < -60) {
            super.reutilizar_globe();
            mi_sleep(13000);
        } else if (super.collision()) {
            brain.setAddVidas();
            super.reutilizar_globe();
            super.getPaneldraw().repaint(0, 0, super.getPaneldraw().getWidth(), super.areaRepaintimg());
            mi_sleep(13000);
        }
        super.movimiento(super.getY(), 10, 10);
        super.setYrestar(5);
        super.getPaneldraw().repaint(super.getX() - 5, super.getY() - 5, getWidth_bubleImg() + 10, getHeight_bubleImg() + 10);
        Thread.sleep(60);
    }

    static void mi_sleep(int minimo) throws InterruptedException {
        sleep((int) (Math.random() * 3000) + minimo);
    }

    public void run() {
        while (true) {
            try {
                moverestrella();
            } catch (InterruptedException | LineUnavailableException ex) {
                Logger.getLogger(Estrellavida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reinicio() {
        super.y = super.getPaneldraw().getHeight() + 15;
        resume();
    }

    public void paused() {
        super.y = super.getPaneldraw().getHeight() + 15;
        suspend();
    }

    public void detener() {
        System.out.println("estrella detenida");
        stop();
    }

}
