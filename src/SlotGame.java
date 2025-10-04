/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.security.SecureRandom;

/**
 *
 * @author Gui
 */
public abstract class SlotGame {
    private static int calcularSlot(){
        SecureRandom random = new SecureRandom();

        int porcentagem = random.nextInt( 100);
        int simbolo = 1;

        //chance de cair barra em um slot 40%
        if( porcentagem < 40){
            simbolo = 1;
        }// chance de cair cereja em um slot 25%
        else if ( porcentagem < 65 ) {
            simbolo = 2;
        }//chance de cair moeda em um slot 20%
        else if ( porcentagem < 85 ) {
            simbolo = 3;
        } //chance de cair sete em um slot 15%
        else simbolo = 4;

        return simbolo;
    }

    static int[] calcularSequencia(){
        int[] slot = new int[3];

        for (int i = 0; i < 3; i++) {
            slot[i] = calcularSlot();
        }

        return slot;
    }
    static double calcularGanho(int[] sequencia){
        double ganho = 0;

        //ganhos de sequencias de 3 simbolos
        if(sequencia[0] == sequencia[1] && sequencia[1] == sequencia[2]){
            switch (sequencia[0]){
                case 1:
                    ganho = 3;
                case 2:
                    ganho = 5;
                    break;
                case 3:
                    ganho = 7;
                    break;
                case 4:
                    ganho = 20;
                    break;
            }
        }
        // ganhos de sequencias de dois simbolos
        else if(sequencia[0] == sequencia[1]){
            switch (sequencia[0]){
                case 1:
                    ganho = 1; // nao ganha mas nao perde
                case 2:
                    ganho = 1.5;
                    break;
                case 3:
                    ganho = 2;
                    break;
                case 4:
                    ganho = 3;
                    break;
            }
        }
        else if(sequencia[1] == sequencia[2]) {
            switch (sequencia[0]) {
                case 1:
                    ganho = 1; // nao ganha mas nao perde
                case 2:
                    ganho = 1.5;
                    break;
                case 3:
                    ganho = 2;
                    break;
                case 4:
                    ganho = 3;
                    break;
            }
        }

        return ganho;
    }


}
