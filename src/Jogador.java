import java.util.ArrayList;
import java.util.List;
/**
 *  Classe do usuário
 */

public class Jogador {
    private String nome;
    protected double saldo;
    private int quantPartidas;
    private List<ResultadoJogo> historico;

    //Construtores
    public Jogador() {
        this.nome = null;
        this.saldo = 1000;
        this.quantPartidas = 0;
        this.historico = new ArrayList<>();
    }

    public Jogador(String nome, double saldo, int quantPartidas, List<ResultadoJogo> historico) {
        this.nome = nome;
        this.saldo = saldo;
        this.quantPartidas = quantPartidas;
        this.historico = historico;
    }

    //Getters e setters
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return this.saldo;      //Útil pra ver o saldo/pontuação do jogador
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getQuantPartidas() {
        return this.quantPartidas;
    }

    public void setQuantPartidas(int quantPartidas) {
        this.quantPartidas = quantPartidas;
    }

    public List<ResultadoJogo> getHistorico() {
        return this.historico;
    }

    public void setHistorico(List<ResultadoJogo> historico) {
        this.historico = historico;
    }

    //Métodos
    public void adicionarAoHistorico(ResultadoJogo resultado) {
        historico.add(resultado);
    }

    public void verHistorico() {
        //da pra imprimir uma array dos ultimos jogos e seus resultados
    }

    public void resgatarSaldo() {
        //encerraria a run e salva o saldo do jogador no ranking
    }

    public void exibirDados() {
        //printaria o nome, saldo e histórico do jogador
    }

    @Override   //dando override numa função de objetos
    public String toString() {
        return this.nome + " (Saldo: " + this.saldo + ")";
    }

}
