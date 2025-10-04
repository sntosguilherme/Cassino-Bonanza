/**
 *
 * @author larag
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Jogador j = new Jogador();
        SlotGameGUI slotGameRUN= new SlotGameGUI(j);
        slotGameRUN.configurarTexto();
        java.awt.EventQueue.invokeLater(() -> slotGameRUN.setVisible(true));
       
    }
    
}
