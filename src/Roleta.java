import javax.swing.*;                   // Interface gráfica pra java GUI
import java.awt.*;                      // API Abstract Window Toolkit
import java.awt.event.*;                // Interações com a interface
import java.awt.geom.AffineTransform;   // Representação 2D
import java.awt.Component;
import java.security.SecureRandom;      // Gerador de números aleatórioss
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Roleta implements Jogos {
    private JLabel resultado; // Mídia que pode ser inserida depois (texto, imagem) do swing
    private JButton girar;

    private static int[] numeros = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24,
            16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
    };

    private static Set<Integer> vermelhos = new HashSet<>(Arrays.asList(
            32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3
    ));

    private static double sector_deg =  360.0/numeros.length;
    private static double base_deg = 90.0;

    private SecureRandom rng = new SecureRandom();
    private double anguloRoleta = 0;
    private double anguloBola = 0;
    private double velocidadeRoleta = 0;
    private double velocidadeBola = 0;
    private Timer timer;

    private void girarRoleta() {
        velocidadeRoleta = 6 + rng.nextDouble()*4;
        velocidadeBola = 18 + rng.nextDouble()*10;
        anguloRoleta = rng.nextDouble()*360;
        anguloBola = rng.nextDouble()*360;

        resultado.setText("Roooooodando");
        resultado.setForeground(new Color(255, 255, 255));
        girar.setEnabled(false);

        if(!timer.isRunning()) {
            timer.start();
        }

        //repaint();
    }

    private static double norm360(double d) {
        d %= 360.0;
        return (d<0) ? d + 360.0 : d;
    }


}
