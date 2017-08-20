package GameLogic;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Controlador {

    private int vidas = 5, level = 1, puntos = 10, muertes = 0;// vidas del jugador
    private int score,maxindex;
    private final Buble[] bombas = new Buble[6];
    private PartidaCanvas pcanvas;
    private Submarino submarinodef;
    private boolean ispaused = true, power_life = false;
    private Clip colisionesaudio, botoneswav, soundplay, soundinicio, soundover, levelup, sounestrella;
    private Estrellavida estrella;
    private Trampa_mina minaexplosion;
    private VentanaFrame windows;

    public Controlador() {
        score = 0;
    }

    public void setPcanvas(PartidaCanvas pcanvas) {
        this.pcanvas = pcanvas;
    }

    public void setSubmarinodef(Submarino submarinodef) {
        this.submarinodef = submarinodef;
    }

    public void set_ventana(VentanaFrame v) {
        this.windows = v;
    }
//states of game / start / paused/ game over /restart    estados del juego, jugando/jugar, pausado, terminado y reiniciar juego

    public void start_game() {
        for (int i = 0; i < bombas.length; i++) {
            bombas[i] = new Buble(this,(i * 110) + 70,((int) (Math.random() * 368) + (pcanvas.getHeight()+100)), i);
            bombas[i].cargarimagenPNG_random("bubble_");
            bombas[i].setCanvas(pcanvas);
            bombas[i].setSubmarino_dfs(submarinodef);
            bombas[i].start();
        }
        estrella = new Estrellavida(this, pcanvas, submarinodef);
        estrella.cargarImgGIF(Load_imgGIF("estrellaanimada"));
        minaexplosion = new Trampa_mina(this, pcanvas, submarinodef);
        minaexplosion.cargarImgGIF(Load_imgGIF("minabombagif"));
        minaexplosion.start();

    }

    void paused_game() {
        if (ispaused) {
            for (int i = 0; i < bombas.length; i++) {
                bombas[i].suspend();
            }
            soundplay.stop();
            estrella.suspend();
            minaexplosion.suspend();
            pcanvas.BottonSelec(3);
            pcanvas.repaint();
            ispaused = false;
        } else {
            for (Buble bomba : bombas) {
                bomba.resume();
            }
            estrella.resume();
            minaexplosion.resume();
            soundplay.start();
            pcanvas.BottonSelec(0);
            pcanvas.repaint();
            ispaused = true;
        }
    }

    public void gameover() {
        try {
            for (Buble bomba : bombas) {
                bomba.setPararHilo(false);
            }
            estrella.detener();
            minaexplosion.detener();
            pcanvas.add(pcanvas.getAyuda());
            pcanvas.add(pcanvas.getAtras());
            System.out.println("game over  lanzado");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void restart_game(int tipo) {
        pcanvas.setPlay(tipo);
        score = 0;
        setVidas(5);
        setMuertes(-5);
        setLevel(1);
        setPower_life(false);
        submarinodef.setX(pcanvas.getWidth() / 2);
        submarinodef.restar_spd();
        pcanvas.repaint();
        if (tipo == 1) 
            start_game();
        System.out.println("play en el controlador "+pcanvas.getPlay());

    }
//sonidos del juego    sounds of game

    public void sound_colision() throws LineUnavailableException {
        try {
            colisionesaudio = AudioSystem.getClip();
            colisionesaudio.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/Bubbling-SoundBible.wav")));
            FloatControl gainControl = (FloatControl) colisionesaudio.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            colisionesaudio.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_button() throws LineUnavailableException {
        try {
            botoneswav = AudioSystem.getClip();
            botoneswav.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/botones.wav")));
            botoneswav.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_game() throws LineUnavailableException {
        try {
            soundplay = AudioSystem.getClip();
            soundplay.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/dragon_ball_z_8_bits.wav")));
            soundplay.getLevel();
            FloatControl gainControl = (FloatControl) soundplay.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            soundplay.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_start() throws LineUnavailableException {
        try {
            soundinicio = AudioSystem.getClip();
            soundinicio.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/sonidogame.wav")));
            soundinicio.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_gameover() throws LineUnavailableException {
        try {
            soundover = AudioSystem.getClip();
            soundover.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/super_mario_muerte.wav")));
            FloatControl gainControl = (FloatControl) soundover.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            soundover.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_level() throws LineUnavailableException {
        try {
            levelup = AudioSystem.getClip();
            levelup.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/level_up1.wav")));
            FloatControl gainControl = (FloatControl) levelup.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            levelup.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sound_star() throws LineUnavailableException {
        try {
            sounestrella = AudioSystem.getClip();
            sounestrella.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/cartoonestrella.wav")));
            sounestrella.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicialsound_stop() {
        soundinicio.stop();
    }

    public void playsound_stop() {
        soundplay.stop();
    }

    public void controldevidas(int id) throws LineUnavailableException {
        bombas[id].cargarimagenPNG_random("bubble_");
        vidas -= 1;
        setMuertes(1);
        if (getVidas() <= 2 & !power_life) {
            setPower_life(true);
            estrella.start();
            pcanvas.repaint();
        }
//        else if (getVidas() == 0) {
//            escribirscore();
//            leerarchivo();
//            pcanvas.setPlay(2);
//            playsound_stop();
//            sound_gameover();
//            gameover();
//            pcanvas.getAyuda().addMouseListener(v);
//            pcanvas.getAtras().addMouseListener(v);
//        }
        pcanvas.repaint();
    }

    public void add_score(int id) throws LineUnavailableException {
        bombas[id].cargarimagenPNG_random("bubble_");
        sound_colision();
        ++score;
        if (score == puntos & getLevel() < 10) {
            sound_level();
            aumentarspd(4);
            puntos += 10;
            submarinodef.setSpdmax(2);
            setLevel(++level);
        }
    }

    public void setMuertes(int muerte) {
        this.muertes += muerte;
        if (muertes < 0) {
            muertes = 0;
        } else if (muertes < 2) {
            pcanvas.setColorvida(Color.GREEN);
        } else if (muertes == 2) {
            pcanvas.setColorvida(Color.YELLOW);
        } else if (muertes == 4) {
            pcanvas.setColorvida(Color.RED);
        }else if (muertes >= 10){
            escribirscore();
            leerarchivo();
            pcanvas.setPlay(2);
            playsound_stop();
            try {
                sound_gameover();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameover();
            pcanvas.getAyuda().addMouseListener(windows);
            pcanvas.getAtras().addMouseListener(windows);
        }
    }

    public void setPower_life(boolean power_life) {
        this.power_life = power_life;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void aumentarspd(int spd) {
        for (int i = 0; i < bombas.length; i++) {
            bombas[i].cambiarVel(spd);
        }
    }

    public void setVidas(int v) {
        this.vidas = v;
    }
//este metodo es para recuperar vidas cuando colisiona con la estrella/ add life

    public void setAddVidas() throws LineUnavailableException {
        sound_star();
        this.vidas += 2;
        if (vidas > 5) {
            vidas = 5;
        }
        setMuertes(-2);
    }
    public void restarVidas(){
       this.vidas-=2;
       setMuertes(2);
    }
    //escribir en el fichero
    public void escribirscore() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("D:\\project_java\\rescueUndersea\\rescueUndersea\\src\\puntajesTxt\\Puntajes.txt", true);
            pw = new PrintWriter(fichero);
            pw.println(getScore());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
//        leerarchivo();
    }
    int[] puntajes = new int[50];
    
    public void leerarchivo() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("D:\\project_java\\rescueUndersea\\rescueUndersea\\src\\puntajesTxt\\Puntajes.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
//                System.out.println("i  " +i  +  linea);
                if (!"".equals(linea)) {
                    puntajes[i++] = Integer.parseInt(linea);
                }
            }
            Arrays.sort(puntajes,0,i);
            maxindex = puntajes[--i];
//            System.out.println("             score maximo  "+puntajes[--i]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
   // public Image load_imagePNG(String rootimage){ //generico de load image .png
      //  Image img_png = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/"+rootimage+".png"));
       // return img_png; }
    public Image Load_imgGIF(String ruta){
       Image imgif = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/" + ruta+ ".gif"));
       return imgif;
    }
    // metodos para la visualizacion de el add_score. el getCanvas 
    public int getVidas() {
        return vidas;
    }

    public int getMuertes() {
        return muertes;
    }

    public boolean isPower_life() {
        return power_life;
    }

    public int getScore() {
        return score;
    }
    public int getScoreMax() {
        return maxindex;
    }
    
    public int getLevel() {
        return level;
    }

    public PartidaCanvas getPcanvas() {
        return pcanvas;
    }

    public Image getMinaexplosionImg() {
        return minaexplosion.getBubleImg();
    }

    public Trampa_mina getMinaexplosion() {
        return minaexplosion;
    }
    
    

    // metodos geters para el submarino y las imagenes 
    public Image getSubmerinoImg() {
        return submarinodef.getSubmarineImg();
    }

    public Image getestrellaimg() {
        return estrella.getBubleImg();
    }

    public Submarino getSubmarino() {
        return submarinodef;
    }

    public Estrellavida getEstrella() {
        return estrella;
    }

    public int getSubmarino_posX() {
        return submarinodef.getX();
    }

    public int getSubmarino_posY() {
        return submarinodef.getY();
    }

    public int getWidthSubmarino() {
        return submarinodef.getWidth_Imgsub();
    }

    public Buble[] getBombas() {
        return bombas;
    }
    //sonidos get

}
