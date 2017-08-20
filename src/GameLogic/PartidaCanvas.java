/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

//import static canastapoedos.Controlador.add_score;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
//import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author karmen
 */
public class PartidaCanvas extends JPanel {

    //private Buble globe;
    private Image  fondogame, cuadricula, burbujas, title, botones,aniburbu,over,pause;
    private Color colorvida = Color.GREEN;
    private int play = 0, botonesid = 0;
    private Controlador controlgame;
    private JLabel lbl_start, lbl_acercade, ayuda, atras;

    public PartidaCanvas(Buble globe, int width, int height) {
        setBounds(0, 0, width, height);
        setBackground(Color.magenta);
        //this.globe = globe;
    }

    public PartidaCanvas(Controlador globos, int width, int height) {
        setBounds(0, 0, width, height);
        this.controlgame = globos;
        setLayout(null);
        lbl_start = new JLabel();
        lbl_acercade = new JLabel();
        ayuda = new JLabel();
        atras = new JLabel("soy atras");
        burbujas = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/bubbles.png"));
        fondogame = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/background.png"));
        cuadricula = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/bg-marco.png"));
        title = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/title.png"));
        botones = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/greenButton.png"));
        aniburbu = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/animeburbuje.gif"));
        over = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/gameover.png"));
        pause = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/pause.png"));
        ImageIcon a = new ImageIcon(botones);
        lbl_start.setBounds((getWidth() - botones.getWidth(this) + 8) / 2, 198, botones.getWidth(this), botones.getHeight(this));
        lbl_acercade.setBounds((getWidth() - botones.getWidth(this) + 8) / 2, 298, botones.getWidth(this), botones.getHeight(this));
        ayuda.setBounds((getWidth() - botones.getWidth(this) + 8) / 2, 398, botones.getWidth(this), botones.getHeight(this));
        atras.setBounds((getWidth() - botones.getWidth(this) + 8) / 2, 465, botones.getWidth(this), botones.getHeight(this));
        atras.setForeground(Color.ORANGE);
        atras.setBackground(colorvida);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Font f = new Font("Ravie", 3, 24);//Ravie,Forte
        g2d.setFont(f);
        //inicio del juego
        paint_Presentacion(g2d);
        //play del juego
        paint_Playgame(g2d);
        paint_Gameover(g2d);      
    }

    void paint_Presentacion(Graphics2D g2) {
       
        if (play == 0) {
            g2.drawImage(fondogame, 0, 0, getWidth(), getHeight(), this);
            g2.drawImage(burbujas, 0, 0, this);
            g2.drawImage(aniburbu, getWidth()-aniburbu.getWidth(this), getHeight()-aniburbu.getHeight(this)-110, this);
            g2.drawImage(aniburbu, 270, getHeight()-aniburbu.getHeight(this)-110, this);
            int xcuadricuola = (getWidth() - cuadricula.getWidth(this)) / 2;
            int ycuadro = (getHeight() / 2) - (cuadricula.getHeight(this) / 2);
            g2.drawImage(cuadricula, xcuadricuola, ycuadro, null);
            g2.drawImage(title, xcuadricuola - 25, ycuadro - 40, 403, 131, null);
            g2.setColor(Color.WHITE);
            if (botonesid == 0) {
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 100, null);
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 200, null);
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 300, null);
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 368, null);
                super.add(lbl_start);
                super.add(lbl_acercade);
                super.add(ayuda);
                g2.drawString("START", xcuadricuola + 120, ycuadro + 140);
                g2.drawString("ABOUT", xcuadricuola + 122, ycuadro + 240);
                g2.drawString("HELP", xcuadricuola + 135, ycuadro + 340);
                super.add(atras);
            } else if (botonesid == 1) {
                Font f = new Font("Calibri", 3, 24);//Ravie,Forte
                g2.setFont(f);
                String acercade = "DESARROLLADORES CRISTIAM DIAZ JUAN CAMILO CAVIEDES";
                g2.drawString(acercade.substring(0, 15), xcuadricuola + 75, ycuadro + 120);
                g2.drawString(acercade.substring(15, 29), xcuadricuola + 95, ycuadro + 200);
                g2.drawString(acercade.substring(29), xcuadricuola + 50, ycuadro + 230);
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 368, null);
                g2.drawString("BACK", xcuadricuola + 145, ycuadro + 405);
            }else if (botonesid == 2){
                Font f = new Font("Calibri", 3, 24);//Ravie,Forte
                g2.setFont(f);
                String help = "UTILIZE LAS TECLAS <- -> PARA MOVERSE Y RESCATAR LAS ESPECIES"
                        + " MARINAS PULSE SPACE PARA PAUSAR ";
                g2.drawString(help.substring(0, 29), xcuadricuola + 20, ycuadro + 120);
                g2.drawString(help.substring(29,48), xcuadricuola + 20, ycuadro + 148);
                g2.drawString(help.substring(48, 70), xcuadricuola + 20, ycuadro + 178);
                g2.drawString(help.substring(70), xcuadricuola + 20, ycuadro + 282);
                g2.drawImage(botones, xcuadricuola + 65, ycuadro + 368, null);
                g2.drawString("BACK", xcuadricuola + 145, ycuadro + 405);
            }

        }
    }

    private void paint_Playgame(Graphics2D g2) {
        if (play == 1) {
            g2.drawImage(fondogame, 0, 0, this);
            g2.drawImage(controlgame.getSubmerinoImg(), controlgame.getSubmarino_posX(), controlgame.getSubmarino_posY(), controlgame.getWidthSubmarino(), 80, this);
            //dibuja la imagen del globo  / draw the globes 
            for (int i = 0; i < controlgame.getBombas().length; i++) {
                g2.drawImage(controlgame.getBombas()[i].getBubleImg(), controlgame.getBombas()[i].getX(), controlgame.getBombas()[i].getY(),75,75, null);
            }
            g2.drawImage(controlgame.getMinaexplosionImg(),controlgame.getMinaexplosion().getX() , controlgame.getMinaexplosion().getY(),65,65, null);
            //dibuja el  puntaje /  add_score  
            Font f = new Font("Calibre", 2, 26);//Ravie,Forte
            g2.setColor(Color.WHITE);
            g2.setFont(f);
            String puntaje ="Level "+ controlgame.getLevel()+" - "+Integer.toString(controlgame.getScore());
            g2.drawString(puntaje, 10, 25);
            //dibuja la barra de vida /  draw the  life
            g2.setColor(getColorvida());
            g2.fillRect(getWidth() - 230, 6, 200 - (controlgame.getMuertes() * 40), 15);
            g2.setColor(Color.BLACK);
            g2.drawRect(getWidth() - 231, 5, 201, 16);
            //dibuja la estrella / draw star
            if (controlgame.isPower_life()) {
                g2.drawImage(controlgame.getestrellaimg(), controlgame.getEstrella().getX(),controlgame.getEstrella().getY(),70,70, null);               
            }
            if (botonesid == 3) {
                int xcuadricuola = (getWidth() - cuadricula.getWidth(this)) / 2;
                int ycuadro = (getHeight() / 2) - (cuadricula.getHeight(this) / 2);
                g2.drawImage(cuadricula, xcuadricuola, ycuadro, null);
                g2.setColor(Color.WHITE);
                g2.drawImage(pause, xcuadricuola+80, ycuadro+200,200, 72, null);
            }
        }
    }

    private void paint_Gameover(Graphics2D g2) {
        if (play == 2) {
            g2.drawImage(fondogame, 0, 0, getWidth(), getHeight(), this);
            int xcuadricuola = (getWidth() - cuadricula.getWidth(this)) / 2;
            int ycuadro = (getHeight() / 2) - (cuadricula.getHeight(this) / 2);
            g2.drawImage(cuadricula, xcuadricuola, ycuadro, null);
            g2.drawImage(botones, xcuadricuola + 65, ycuadro + 368, null);
            g2.drawImage(botones, xcuadricuola + 65, ycuadro + 300, null);
            g2.setBackground(Color.WHITE);
            Font f = new Font("Ravie", 2, 40);//Ravie,Forte
            g2.setFont(f);
            g2.drawImage(over,xcuadricuola+25, 150,290, 62, null);
            Font G = new Font("Ravie", 2, 20);//Ravie,Forte
            g2.setFont(G);
            g2.setColor(Color.WHITE);
            g2.drawString("Score         "+Integer.toString(controlgame.getScore()), xcuadricuola+70,ycuadro+150 );
            g2.drawString("Score max "+Integer.toString(controlgame.getScoreMax()), xcuadricuola+70,ycuadro+200 );
            g2.drawString("MENU", xcuadricuola + 130, ycuadro + 402);
            g2.drawString("PLAY AGAIN", xcuadricuola + 105, ycuadro + 340);

        }
    }
     public Image load_imagePNG(String rootimage,Image imagen){
        imagen = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/"+rootimage+".png"));
        return imagen;
     }
   
    public void setPlay(int b) {
        play = b;
        System.out.println("entra "+b +" play en canvas "+play);  
    }

    public void BottonSelec(int b) {
        botonesid = b;
    }

    public void setColorvida(Color a1) {
        this.colorvida = a1;
    }
// geters 
    
    public Color getColorvida() {
        return colorvida;
    }

    public int getPlay() {
        return play;
    }
    public JLabel getLbl_start() {
        return lbl_start;
    }

    public JLabel getLbl_Acercade() {
        return lbl_acercade;
    }

    public JLabel getAyuda() {
        return ayuda;
    }

    public JLabel getAtras() {
        return atras;
    }

}
