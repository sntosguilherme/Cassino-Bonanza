import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {

    static String[] mostrarTop10(){
        List<Jogador> leaderboard = new ArrayList<>();
        leaderboard = listarJSON();
        List<Jogador> top10 = leaderboard.stream().sorted(Comparator.comparing(Jogador::getSaldo).reversed()).limit(10).toList();
        String[] stringTop10 = new String[10];
        
        for (int i = 0; i < top10.size(); i++) {
            stringTop10[i] = top10.get(i).toString();
        }
        
        
        return stringTop10;
    }


    private static ArrayList<Jogador> listarJSON(){
        List<Jogador> leaderboard = new ArrayList<>();
        Gson gson1 = new Gson();

        //obtem o tipo da lista para poder colocar o json corretamente
        Type listType = new TypeToken<ArrayList<Jogador>>(){}.getType();
        String pontuacoesJson = "pontuacoes.json";

        FileReader reader = null;
        try {
            reader = new FileReader(pontuacoesJson);
            List<Jogador> tempLeaderboard = gson1.fromJson(reader, listType);
            if (tempLeaderboard != null) {
                leaderboard = (ArrayList<Jogador>)tempLeaderboard;
            }

        } catch (FileNotFoundException e) {
            return (ArrayList<Jogador>) leaderboard;

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return (ArrayList<Jogador>) leaderboard;
    }

    static void atualizarLeaderBoard(Jogador jogador){
        String pontuacoesJson = "pontuacoes.json";
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Jogador> leaderboard = new ArrayList<>();

        leaderboard = listarJSON();

        //adiciona o jogador a arraylist do json.
        leaderboard.add(jogador);

        //reescreve o arquivo json com o novo jogador incluido.
        try (FileWriter writer = new FileWriter(pontuacoesJson)){
            gson1.toJson(leaderboard, writer);
            System.out.println("jogador adicionado ao ranking.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
