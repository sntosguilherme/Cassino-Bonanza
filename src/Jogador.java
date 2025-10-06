import java.util.ArrayList;
import java.util.List;
/**
 *  Classe do usuário
 */

public class Jogador {
    private String nome;
    protected double saldo;

    //Construtores
    public Jogador() {
        this.nome = null;
        this.saldo = 1000;
    }

    public Jogador(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo ;
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

    @Override   //dando override numa função de objetos
    public String toString() {
        return this.nome + " ----------------------------------------- " + this.saldo + " PTS";
    }

}
