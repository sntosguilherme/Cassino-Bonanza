import javax.swing.*;                   // Interface gráfica pra java GUI
import java.awt.*;                      // API Abstract Window Toolkit
import java.awt.geom.AffineTransform;
import java.security.SecureRandom;      // Gerador de números aleatórioss
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ItemEvent;

public class Roleta extends JPanel {
    private Jogador j;
    private double apostaMontante;

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
        if(podeApostar()) {
            if(apostaMontante!=0) {
                //interações com jogador
                this.j.saldo -= this.apostaMontante;
                this.saldo.setText("SALDO: " + this.j.getSaldo());
                configurarTexto();
                this.aposta.setText("SUA APOSTA: 0.0");

                //desativando os botões
                aposta5.setEnabled(false);
                aposta10.setEnabled(false);
                aposta50.setEnabled(false);
                zeraAposta.setEnabled(false);
                allIn.setEnabled(false);

                //física e animação da roleta
                velocidadeRoleta = 6 + rng.nextDouble()*4;
                velocidadeBola = 18 + rng.nextDouble()*10;
                anguloRoleta = rng.nextDouble()*360;
                anguloBola = rng.nextDouble()*360;

                resultado.setText("Rodando...");
                resultado.setForeground(new Color(255, 255, 255));
                girar.setEnabled(false);

                if(!timer.isRunning()) {
                    timer.start();
                }

                repaint();
            }
            else {
                JOptionPane.showMessageDialog(null, "APOSTE UM VALOR MAIOR QUE ZERO!");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "ESCOLHA UM COR PARA APOSTAR!");
        }
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
            this.j.saldo += calcularGanho(cor);
            this.apostaMontante = 0.0;
            configurarTexto();

            //reiniciando a escolha de cor
            corEscolhida = "0";
            escolhaCor.setSelectedIndex(0);

            //reativando os botões
            aposta5.setEnabled(true);
            aposta10.setEnabled(true);
            aposta50.setEnabled(true);
            zeraAposta.setEnabled(true);
            allIn.setEnabled(true);
        }
        repaint();
    }

    private void iniciarComponentes() {
        aposta = new javax.swing.JLabel();
        saldo = new javax.swing.JLabel();
        aposta5 = new javax.swing.JButton();
        aposta10 = new javax.swing.JButton();
        aposta50 = new javax.swing.JButton();
        zeraAposta = new javax.swing.JButton();
        allIn = new javax.swing.JButton();

        //configuração do combobox
        String[] opcoes = { "Selecione a cor apostada","Vermelho", "Preto", "Verde" };
        escolhaCor = new javax.swing.JComboBox<>(opcoes);
        escolhaCor.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
        escolhaCor.setBackground(Color.lightGray);
        escolhaCor.setForeground(Color.BLACK);
        escolhaCor.setMaximumSize(new java.awt.Dimension(200,16));

        escolhaCor.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && !e.getItem().equals("Selecione a cor apostada")) {
                System.out.println("Cor selecionada: " + e.getItem());
                corEscolhida = e.getItem().toString();
            }
            else {
                corEscolhida = "0";
            }
        });

        //configuração dos labels
        aposta.setFont(new java.awt.Font("Segoe UI Black", Font.PLAIN, 18));
        aposta.setForeground(new java.awt.Color(204, 0, 0));
        aposta.setText("SUA APOSTA: 0.0");
        aposta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        aposta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 0, 0), 2),
                BorderFactory.createEmptyBorder(4, 15, 4, 15)
        ));

        saldo.setFont(new java.awt.Font("Segoe UI Black", Font.PLAIN, 18));
        saldo.setForeground(new java.awt.Color(255, 204, 0));
        saldo.setText("SALDO:");
        saldo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(4, 15, 4, 15)
        ));

        aposta5.setBackground(new java.awt.Color(51, 153, 0));
        aposta5.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        aposta5.setText("5");
        aposta5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        aposta5.setPreferredSize(new java.awt.Dimension(25, 25));
        aposta5.addActionListener(this::aposta5ActionPerformed);

        aposta10.setBackground(new java.awt.Color(51, 153, 0));
        aposta10.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        aposta10.setText("10");
        aposta10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        aposta10.setPreferredSize(new java.awt.Dimension(25, 25));
        aposta10.addActionListener(this::aposta10ActionPerformed);

        aposta50.setBackground(new java.awt.Color(51, 153, 0));
        aposta50.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        aposta50.setText("50");
        aposta50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        aposta50.setPreferredSize(new java.awt.Dimension(25, 25));
        aposta50.addActionListener(this::aposta50ActionPerformed);

        zeraAposta.setBackground(new java.awt.Color(255, 255, 0));
        zeraAposta.setFont(new java.awt.Font("Segoe UI Black", Font.PLAIN, 12));
        zeraAposta.setText("ZERAR APOSTA");
        zeraAposta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        zeraAposta.setPreferredSize(new java.awt.Dimension(110, 25));
        zeraAposta.addActionListener(this::zeraApostaActionPerformed);

        allIn.setBackground(new java.awt.Color(204, 0, 0));
        allIn.setForeground(new Color(35, 35, 35));
        allIn.setFont(new java.awt.Font("Segoe UI Black", Font.PLAIN, 12));
        allIn.setText("ALL IN");
        allIn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        allIn.setPreferredSize(new java.awt.Dimension(50, 25));
        allIn.setPreferredSize(new java.awt.Dimension(47, 23));
        allIn.addActionListener(this::allinActionPerformed);

    }

    //aposta
    public void configurarTexto(){
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
        this.saldo.setText("SALDO: " + this.j.getSaldo());
    }

    private void verificaApostaMaxima(){
        if(this.apostaMontante > this.j.getSaldo()){
            this.apostaMontante = this.j.getSaldo();
            this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
        }
    }

    private boolean podeApostar() {
        return !corEscolhida.equals("0");
    }

    private double calcularGanho(String cor) {
        if(corEscolhida.equalsIgnoreCase(cor)) {
            if(corEscolhida.equalsIgnoreCase("Verde")) {
                System.out.println("Verde - "+ this.apostaMontante*36);
                return this.apostaMontante*36;
            }
            else {
                System.out.println("Preto/Vermelho - "+ this.apostaMontante*2);
                return this.apostaMontante*2;
            }
        }
        return 0;
    }

    //ação dos botões
    private void aposta5ActionPerformed(java.awt.event.ActionEvent evt) {
        this.apostaMontante += 5;
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
        verificaApostaMaxima();
    }

    private void aposta10ActionPerformed(java.awt.event.ActionEvent evt) {
        this.apostaMontante += 10;
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
        verificaApostaMaxima();
    }

    private void aposta50ActionPerformed(java.awt.event.ActionEvent evt) {
        this.apostaMontante += 50;
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
        verificaApostaMaxima();
    }

    private void zeraApostaActionPerformed(java.awt.event.ActionEvent evt) {
        this.apostaMontante = 0;
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
    }

    private void allinActionPerformed(java.awt.event.ActionEvent evt) {
        this.apostaMontante = this.j.getSaldo();
        this.aposta.setText("SUA APOSTA: " + this.apostaMontante);
    }


    public Roleta(Jogador j) {
        this.j = j;
        this.apostaMontante = 0;

        iniciarComponentes();
        configurarTexto();
        setPreferredSize(new Dimension(720, 950));
        setBackground(new Color(35,35, 35));

        timer = new Timer(16, _ -> tick());

        //config label
        resultado = new JLabel("Faça sua aposta!", SwingUtilities.LEFT);
        resultado.setFont(new Font("Segoe UI Black", Font.BOLD, 26));
        resultado.setForeground(Color.WHITE);
        resultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        //conf do botão girar
        girar = new JButton("GIRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!isEnabled()) {
                    g2.setColor(new Color(80, 90, 100));
                }
                else if(getModel().isPressed()) {
                    g2.setColor(new Color(255, 204, 51));
                }
                else if(getModel().isRollover()) {
                    g2.setColor(new Color(255, 204, 51));
                }
                else {
                    g2.setColor(new Color(255, 204, 51));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                int tw = fm.stringWidth(getText());
                int th = fm.getAscent();
                g2.drawString(getText(), (getWidth() - tw)/2, (getHeight() + th)/ 2-3);
                g2.dispose();
            }
        };
        girar.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
        girar.setPreferredSize(new Dimension(100,40));
        girar.setContentAreaFilled(false);
        girar.setFocusPainted(false);
        girar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        girar.addActionListener(_ -> girarRoleta());

        //Layout
        //painel esquerda
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(new Color(35, 35, 35));

        JPanel saldoRow = new JPanel(new FlowLayout(FlowLayout.LEFT,40,0));
        saldoRow.setOpaque(false);
        saldoRow.add(saldo);
        left.add(saldoRow);
        left.add(Box.createVerticalStrut(5));

        JPanel apostaLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT,40,0));
        apostaLabelRow.setOpaque(false);
        apostaLabelRow.add(aposta);
        left.add(apostaLabelRow);
        left.add(Box.createVerticalStrut(5));

        JPanel apostasBtRow = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        apostasBtRow.setOpaque(false);
        apostasBtRow.add(new JLabel("       "));
        apostasBtRow.add(zeraAposta);
        apostasBtRow.add(aposta5);
        apostasBtRow.add(aposta10);
        apostasBtRow.add(aposta50);
        apostasBtRow.add(allIn);
        left.add(apostasBtRow);
        left.add(Box.createVerticalStrut(5));

        //painel direita
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(35, 35, 35));

        JPanel corRow = new JPanel(new FlowLayout(FlowLayout.LEFT,11,30));
        corRow.setOpaque(false);
        corRow.add(escolhaCor);
        right.add(corRow);

        JPanel girarRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 0));
        girarRow.setOpaque(false);
        girarRow.add(girar);
        right.add(girarRow);
        right.add(Box.createVerticalStrut(20));

        setLayout(new BorderLayout());

        //texto superior
        JPanel resultRow = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        resultRow.setOpaque(false);
        resultRow.add(resultado);
        add(resultRow);

        //painel sul
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
        south.setBackground(new Color(35, 35, 35));
        south.add(left);
        south.add(right);

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
//        SwingUtilities.invokeLater(() ->{
//            Jogador j = new Jogador();
//            JFrame f = new JFrame("Roleta Bonanza");
//            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            f.setContentPane(new Roleta(j));
//            f.pack();
//            f.setLocationRelativeTo(null);
//            f.setVisible(true);
//            }
//        );
    }

    //declaração de variáveis
    private final JLabel resultado;
    private final JButton girar;
    private javax.swing.JButton aposta5;
    private javax.swing.JButton aposta10;
    private javax.swing.JButton aposta50;
    private javax.swing.JButton zeraAposta;
    private javax.swing.JButton allIn;
    private JLabel aposta;
    private JLabel saldo;
    private javax.swing.JComboBox<String> escolhaCor;
    private String corEscolhida = "0";
}
