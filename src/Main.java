import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o tamanho a ser usado para os baldes.\n");
        int bucketSize = scan.nextInt();
        System.out.println("Digite o número de bits a serem usados para as pseudo-chaves.\n");
        int numBits = scan.nextInt();
        System.out.println("Digite quantas pseudochaves deseja inserir.\n");
        int totalPseudoKeys = scan.nextInt();
        Directory directory = new Directory(bucketSize, numBits);

        int option = 3;
        while((option != 2 || option != 1)) {
            System.out.println("Digite 1 para fazer o teste com bits aleatórios, " +
                    "e 2 para fazer o teste com bits iniciais identicos");
            option = scan.nextInt();

            if (option == 1) {
                randomBits(directory, numBits, totalPseudoKeys);
                break;
            } else if (option == 2) {
                padronizedBits(directory, numBits, totalPseudoKeys);
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }

        directory.print();
    }

    public static void randomBits(Directory directory, int numBits, int totalPseudoKeys){
        String[] values = new String[totalPseudoKeys];
        Random random = new Random();
        String str = "";
        for(int i = 0; i < totalPseudoKeys; i++){
            str = "";
            for(int j = 0; j < numBits; j++) {
                double randValue = random.nextDouble();
                if (randValue < 0.5) {
                    str += "1";
                } else {
                    str += "0";
                }
            }
            directory.insertPseudoKey(str);
        }
    }

    public static void padronizedBits(Directory directory, int numBits, int totalPseudokeys){
        for(int i = 0; i < totalPseudokeys; i++) {
            String pseudoKey = "11";
            Random random = new Random();
            for (int j = 0; j < 2; j++) {
                double randValue = random.nextDouble();
                if (randValue < 0.5) {
                    pseudoKey += "0";
                } else {
                    pseudoKey += "1";
                }
            }
            directory.insertPseudoKey(pseudoKey);
        }
    }
}
