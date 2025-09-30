public class ResultadoJogo {
    private String jogo;
    private boolean ganhou;
    private double aposta;
    private double valorRecebido;

    //Construtores
    public ResultadoJogo(String jogo, boolean ganhou, double aposta, double valorRecebido) {
        this.jogo = jogo;
        this.ganhou = ganhou;
        this.aposta = aposta;
        this.valorRecebido = valorRecebido;
    }

    public ResultadoJogo() {
        this.jogo = null;
        this.ganhou = false;
        this.aposta = 0;
        this.valorRecebido = 0;
    }

    //Getters e setters
    public String getJogo() {
        return this.jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public boolean isGanhou() {
        return this.ganhou;
    }

    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }

    public double getAposta() {
        return aposta;
    }

    public void setAposta(double aposta) {
        this.aposta = aposta;
    }

    public double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    //Métodos
    public void exibirDados() {
        //printar nome do jogo, resultado (ganhou ou não), valor apostado e valor ganho(ou perdido)
    }

    @Override
    public String toString() {
        //mesma coisa do exibirDados, mas em string :p
        return ""; //coloquei nd por enquanto só pra n ficar aparecendo erro
    }
}
