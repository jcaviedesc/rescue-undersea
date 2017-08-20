package GameLogic;

//import java.awt.Color;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

public class VentanaFrame extends JFrame implements KeyListener, MouseListener {

    private PartidaCanvas gamecanvas;
    private Submarino canasta;
    private Controlador controlerglobos;
    private int estado = 0;// variable para controlar las fases o cambios del juego

    public VentanaFrame() throws LineUnavailableException {
        super("RescueUndersea");
        setSize(816, 682);
        setLocation(200, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        controlerglobos = new Controlador();
        gamecanvas = new PartidaCanvas(controlerglobos, getWidth() - 16, getHeight() - 32);
        controlerglobos.setPcanvas(gamecanvas);
        gamecanvas.getAyuda().addMouseListener(this);
        gamecanvas.getLbl_start().addMouseListener(this);
        gamecanvas.getLbl_Acercade().addMouseListener(this);
        gamecanvas.getAtras().addMouseListener(this);
        controlerglobos.set_ventana(this);
        try {
            controlerglobos.sound_start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(VentanaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        addKeyListener(this);

        add(gamecanvas);
    }

    public void keyTyped(KeyEvent ke) {}

    public void keyPressed(KeyEvent key) {
// movimientod de la defensa o barra con puntas
        if (key.getKeyCode() == KeyEvent.VK_LEFT & estado == 2) {//37 izquierda
            canasta.Move_Left(1);
            canasta.move();
        } else if (key.getKeyCode() == KeyEvent.VK_RIGHT & estado == 2) {//39 derecha
            canasta.Move_Right(1);
            canasta.move();
        } else if (key.getKeyCode() == 32) {
            System.out.println("hola");
            controlerglobos.paused_game();
        }
    }

    public void keyReleased(KeyEvent key) {
        if (estado == 2) {
            if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
                canasta.Move_Right(0);
            } else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
                canasta.Move_Left(0);
            }
            canasta.move();
        }
    }

    public void actionPerformed(ActionEvent ae) {

    }

    public void mouseClicked(MouseEvent me) {
        try {
            controlerglobos.sound_button();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(VentanaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (me.getSource().equals(gamecanvas.getLbl_start())) {
            System.out.println("play game");
            controlerglobos.inicialsound_stop();
            gamecanvas.setPlay(1);
            gamecanvas.repaint();
            canasta = new Submarino(gamecanvas.getWidth() / 2, 35, gamecanvas);
            controlerglobos.setSubmarinodef(canasta);
            controlerglobos.start_game();
            try {
                controlerglobos.sound_game();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(VentanaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            remove_buttons();
            gamecanvas.remove(gamecanvas.getAtras());
            estado = 2;
        } else if (me.getSource().equals(gamecanvas.getLbl_Acercade())) {
            System.out.println("acerca de");
            gamecanvas.BottonSelec(1);
            gamecanvas.repaint();
            remove_buttons();

        } else if (me.getSource().equals(gamecanvas.getAyuda()) & gamecanvas.getPlay() == 0) {
            System.out.println("ayuda");
            gamecanvas.BottonSelec(2);
            gamecanvas.repaint();
            remove_buttons();

        } else if (me.getSource().equals(gamecanvas.getAtras()) & gamecanvas.getPlay() == 2) {
            System.out.println("retorna al menu");
            //gamecanvas.setPlay(0); se puede quitar -estable
            controlerglobos.restart_game(0);
            try {
                controlerglobos.sound_start();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(VentanaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            add_button();
            //gamecanvas.repaint();--

        } else if (me.getSource().equals(gamecanvas.getAtras())) {
            System.out.println("entra en back inicio");
            gamecanvas.BottonSelec(0);
            gamecanvas.repaint();
            add_button();
        }
        if (me.getSource().equals(gamecanvas.getAyuda()) & gamecanvas.getPlay() == 2) {
            System.out.println("help game over replay");
            //gamecanvas.setPlay(1);
            controlerglobos.restart_game(1);
            // gamecanvas.repaint();
            //controlerglobos.start_game();
            try {
                controlerglobos.sound_game();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(VentanaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
 
    public void remove_buttons() {
        
        gamecanvas.remove(gamecanvas.getLbl_start());
        gamecanvas.remove(gamecanvas.getLbl_Acercade());
        gamecanvas.remove(gamecanvas.getAyuda());
       
        //gamecanvas.getStart().removeMouseListener(this);
        //gamecanvas.getLbl_Acercade().removeMouseListener(this);
        //gamecanvas.getAyuda().removeMouseListener(this);
        //gamecanvas.getAtras().addMouseListener(this);
    }

    public void add_button() {
        gamecanvas.add(gamecanvas.getLbl_start());
        gamecanvas.add(gamecanvas.getLbl_Acercade());
        gamecanvas.add(gamecanvas.getAyuda());
        //gamecanvas.getStart().addMouseListener(this);
        //gamecanvas.getAyuda().addMouseListener(this);
        //gamecanvas.getLbl_Acercade().addMouseListener(this);
       
    }
}
