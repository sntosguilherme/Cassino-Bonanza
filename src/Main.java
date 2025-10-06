/**
 *
 * @author larag
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setLocationRelativeTo(null);
        java.awt.EventQueue.invokeLater(() -> menu.setVisible(true));
    }
    
}
