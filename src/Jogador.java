/**
 *  Classe do usuário
 */

public class Jogador {
    public String nome;
    protected double saldo;
    public int quantPartidas;

    //Construtores
    public Jogador() {
        this.nome = null;
        this.saldo = 1000;
        this.quantPartidas = 0;
    }

    public Jogador(String nome, double saldo, int quantPartidas) {
        this.nome = nome;
        this.saldo = saldo;
        this.quantPartidas = quantPartidas;
    }

    //Getters e setters

    //Métodos
    public double verSaldo() {
        return this.saldo;
    }

    public void verHistorico() {
        //da pra imprimir uma array dos ultimos jogos e seus resultados
    }

    public void resgatarSaldo() {
        //encerraria a run e salva o saldo do jogador no ranking
    }

}
