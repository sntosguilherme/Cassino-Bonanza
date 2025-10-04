import javax.swing.*;                   // Interface gráfica pra java GUI
import java.awt.*;                      // API Abstract Window Toolkit
import java.awt.geom.AffineTransform;
import java.security.SecureRandom;      // Gerador de números aleatórioss
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Roleta extends JPanel {
    private final JLabel resultado; // Mídia que pode ser inserida depois (texto, imagem) do swing
    private final JButton girar;

    private static final int[] numeros = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24,
            16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
    };

    private static final Set<Integer> vermelhos = new HashSet<>(Arrays.asList(
        32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3
        )
    );

    private static final double grauSetor =  360.0/numeros.length;
    private static final double grauBase = 90.0;

    private final SecureRandom rng = new SecureRandom();    //random number genarator
    private double anguloRoleta = 0;
    private double anguloBola = 0;
    private double velocidadeRoleta = 0;
    private double velocidadeBola = 0;
    private final Timer timer;

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

        repaint();
    }

    private static double norm360(double d) {
        d %= 360.0;
        return (d<0) ? d + 360.0 : d;
    }

    private static double grauSlotCentral(int index) {
        return norm360(grauBase - (index+0.5) * grauSetor);
    }

    private static double distanciaAngular(double a, double b) {
        double d = Math.abs(norm360(a) - norm360(b));
        return Math.min(d, 360.0 - d);
    }

    //determinar a cor que caiu dependendo do número
    private static String nomeCor(int n) {
        if (n == 0) {
            return "VERDE";
        }
        return vermelhos.contains(n) ? "VERMELHO" : "PRETO";
    }

    //printar o resultado e mudar a cor do fundo dependendo dele
    private void mostrarResultado(int numero, String cor) {
        resultado.setText("Resultado: " + numero + " " + cor);
        if("VERMELHO".equals(cor)) {
            resultado.setForeground(new Color(220,70,60));
        }
        else if("PRETO".equals(cor)) {
            resultado.setForeground(Color.white);
        }
        else {
            resultado.setForeground(new Color(90,170, 60));
        }
    }

    private int slotMaisProximo(double relCWDeg) {
        int melhor = 0;
        double melhorDelta = 1e9;
        for (int i = 0; i <numeros.length; i++) {
            double centro = grauSlotCentral(i);
            double d = distanciaAngular(relCWDeg, centro);

            if(d < melhorDelta) {
                melhorDelta = d;
                melhor = i;
            }
        }
        return melhor;
    }

    private void tick() {
        anguloRoleta = norm360(anguloRoleta + velocidadeRoleta);
        anguloBola = norm360(anguloBola - velocidadeBola);

        velocidadeRoleta *= 0.995;
        velocidadeBola *= 0.985;

        if(velocidadeBola < 0.2) {
            timer.stop();

            double rel = norm360(anguloBola - anguloRoleta);
            int idx = slotMaisProximo(rel);
            int numero = numeros[idx];
            String cor = nomeCor(numero);

            double centro = grauSlotCentral(idx);
            anguloBola = norm360(anguloRoleta + centro);

            mostrarResultado(numero, cor);
            girar.setEnabled(true);
        }
        repaint();
    }

    public Roleta() {
        setPreferredSize(new Dimension(720, 950));
        setBackground(new Color(35,35, 35));

        timer = new Timer(16, e -> tick());

        resultado = new JLabel("Clique para girar", SwingUtilities.LEFT);
        resultado.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultado.setForeground(Color.WHITE);
        resultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        girar = new JButton("Girar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!isEnabled()) {
                    g2.setColor(new Color(80, 90, 100));
                }
                else if(getModel().isPressed()) {
                    g2.setColor(new Color(60, 130, 200));
                }
                else if(getModel().isRollover()) {
                    g2.setColor(new Color(70, 150,220));
                }
                else {
                    g2.setColor(new Color(50, 120, 190));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int tw = fm.stringWidth(getText());
                int th = fm.getAscent();
                g2.drawString(getText(), (getWidth() - tw)/2, (getHeight() + th)/ 2-3);
                g2.dispose();
            }
        };

        girar.setFont(new Font("SansSerif", Font.BOLD, 16));
        girar.setContentAreaFilled(false);
        girar.setFocusPainted(false);
        girar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        girar.addActionListener(e -> girarRoleta());

        //painel sul
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(new Color(35, 35, 35));
        south.add(resultado);
        south.add(Box.createVerticalStrut(10));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setOpaque(false);
        btnRow.add(girar);
        south.add(btnRow);

        setLayout(new BorderLayout());
        add(south, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        int size = Math.min(w, h) - 30;
        int cx = w/2, cy = (h-70)/2 + 20;
        int rOuter = size/2;
        int x = cx - rOuter, y = cy - rOuter;

        g2.setColor(new Color(25, 25, 25));
        g2.fillOval(x - 18, y - 18, (rOuter + 18)*2,(rOuter+ 18)*2);

        AffineTransform saved = g2.getTransform();
        g2.rotate(Math.toRadians(-anguloRoleta), cx, cy);

        for(int i = 0; i < numeros.length; i++) {
            int num = numeros[i];
            Color pocket = (num == 0) ? new Color(85,160, 40) :
                    (vermelhos.contains(num)) ? new Color(200, 60, 50) : new Color(45, 45, 45);

            int start = (int) Math.round(grauBase - i * grauSetor);
            int extent = (int) Math.round(-grauSetor);

            g2.setColor(pocket);
            g2.fillArc(x, y, rOuter *2, rOuter*2, start, extent);
            g2.setStroke(new BasicStroke(2f));
            g2.drawArc(x+1, y+1, rOuter*2 - 2, rOuter*2 -2, start, extent);
        }

        //círculo interno da roleta
        int rInner = (int) (rOuter * 0.82);
        g2.setColor(new Color(35, 35, 35));
        g2.fillOval(cx - rInner, cy - rInner, rInner*2, rInner*2);

        g2.setFont(new Font("SansSerif", Font.BOLD, (int) (rOuter * 0.07)));

        for(int i = 0; i < numeros.length; i++) {
            double theta = Math.toRadians(grauBase - (i+0.5) *grauSetor);
            double tx = cx + Math.cos(theta) * (rOuter * 0.92);
            double ty = cy - Math.sin(theta) * (rOuter * 0.92);

            String text = String.valueOf(numeros[i]);
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(text);
            int th = fm.getAscent();

            AffineTransform t = g2.getTransform();
            g2.translate(tx, ty);
            g2.rotate(Math.toRadians(90 - (grauBase - (i+0.5) * grauSetor)));
            g2.setColor(Color.WHITE);
            g2.drawString(text, -tw/2, th/2);
            g2.setTransform(t);
        }

        int rHub = (int) (rOuter * 0.28);
        g2.setColor(new Color(25, 25, 25));
        g2.fillOval(cx - rHub, cy - rHub, rHub*2, rHub*2);

        g2.setTransform(saved);

        int rBall = (int) (rOuter * 0.88);
        double ballTheta = Math.toRadians(anguloBola);
        int bx = (int)Math.round(cx + Math.cos(ballTheta) * rBall);
        int by = (int)Math.round(cy - Math.sin(ballTheta) * rBall);
        int ballSize = (int)(rOuter * 0.045);
        g2.setColor(new Color(245, 245, 245));
        g2.fillOval(bx - ballSize/2, by - ballSize/2, ballSize, ballSize);
        g2.setColor(new Color(180,180,180));
        g2.drawOval(bx - ballSize/2, by - ballSize/2, ballSize, ballSize);

        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            JFrame f = new JFrame("Roleta Bonanza");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new Roleta());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            }
        );
    }
}
