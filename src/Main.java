/**
 *
 * @author larag
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*Jogador j = new Jogador();
        SlotGameGUI slotGameRUN= new SlotGameGUI(j);
        slotGameRUN.configuracaoInicial();
        java.awt.EventQueue.invokeLater(() -> slotGameRUN.setVisible(true));*/
        
        RankingGUI ranking = new RankingGUI();
        ranking.configuracaoInicial();
        java.awt.EventQueue.invokeLater(() -> ranking.setVisible(true));
        
       
       
    }
    
}
