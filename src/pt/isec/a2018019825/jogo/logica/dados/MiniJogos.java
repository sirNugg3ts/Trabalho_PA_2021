package pt.isec.a2018019825.jogo.logica.dados;

import pt.isec.a2018019825.jogo.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MiniJogos {

    private boolean typeRacerEnabled;

    private ArrayList<String> listaPalavras;
    private int palavrasLidas;

    public MiniJogos() {
        palavrasLidas = 0;

        File ficheiroTypeRacer = new File("typeracer.txt");
        if (!ficheiroTypeRacer.canRead()) {
            System.err.println("Erro ao ler ficheiro texto typeracer.txt para minijogos");
            System.err.println("O programa irá continuar sem este minijogo");
            typeRacerEnabled = false;
        } else {
            try {

                Scanner sc = new Scanner(ficheiroTypeRacer);
                listaPalavras = new ArrayList<>();
                while (sc.hasNextLine()) {
                    listaPalavras.add(sc.nextLine());
                    palavrasLidas++;
                }
                System.out.println("Ficheiro texto lido com sucesso: " + palavrasLidas + " palavras lidas");
                typeRacerEnabled = true;
                sc.close();
            } catch (FileNotFoundException e) {
                System.err.println("Ok não era suposto chegar aqui");
                System.err.println(e);
                typeRacerEnabled = false;
            }
        }
    }

    public boolean mathGame() {
        long start = System.currentTimeMillis();
        int acertados = 0;

        while (System.currentTimeMillis() - start < 30000) {
            int n1 = (int) ((Math.random() * 20) + 1);
            int n2 = (int) ((Math.random() * 20) + 1);

            int op = (int) (Math.random() * 4);

            int resposta;

            switch (op) {
                case 0: //+
                    resposta = Utils.pedeInteiro(n1 + "+" + n2 + "=");
                    if (n1 + n2 == resposta) {
                        System.out.println("Correto!");
                        if (System.currentTimeMillis() - start < 30000)
                            acertados++;
                        else
                            System.out.println("Já passaram os 30 segundos!");
                    } else
                        System.out.println("Errado");
                    break;
                case 1:// -
                    resposta = Utils.pedeInteiro(n1 + "-" + n2 + "=");
                    if (n1 - n2 == resposta) {
                        System.out.println("Correto!");
                        if (System.currentTimeMillis() - start < 30000)
                            acertados++;
                        else
                            System.out.println("Já passaram os 30 segundos!");
                    } else
                        System.out.println("Errado");
                    break;
                case 2: // *
                    resposta = Utils.pedeInteiro(n1 + "*" + n2 + "=");
                    if (n1 * n2 == resposta) {
                        System.out.println("Correto!");
                        if (System.currentTimeMillis() - start < 30000)
                            acertados++;
                        else
                            System.out.println("Já passaram os 30 segundos!");
                    } else
                        System.out.println("Errado");
                    break;
                case 3: // /
                    resposta = Utils.pedeInteiro(n1 + "/" + n2 + "=");
                    if (n1 / n2 == resposta) {
                        System.out.println("Correto!");
                        if (System.currentTimeMillis() - start < 30000)
                            acertados++;
                        else
                            System.out.println("Já passaram os 30 segundos!");
                    } else
                        System.out.println("Errado");
                    break;

            }
        }
        System.out.println("Tempo terminou");
        return acertados > 4;
    }

    public boolean typeRacer() {

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        Scanner consoleReader = new Scanner(System.in);

        do {
            int x = random.nextInt(palavrasLidas - 1);
            if (!numbers.contains(x)) {
                numbers.add(x);
                sb.append(listaPalavras.get(x)).append(" ");
            }
        } while (numbers.size() < 5);

        String frase = sb.toString().trim();

        long start = System.currentTimeMillis();
        System.out.println("Tens " + (frase.length() / 2) + " segundos para escrever!");
        System.out.println(frase);


        if (frase.equals(consoleReader.nextLine())) {
            if (System.currentTimeMillis() - start <= (frase.length() * 1000L) / 2) {
                System.out.println("Resposta correta, demoraste " + (System.currentTimeMillis() - start) / 1000 + " segundos");
                return true;
            } else {
                System.out.println("Resposta correta, mas demoraste demasiado tempo: " + (System.currentTimeMillis() - start) / 1000 + " segundos");
                return false;
            }
        } else {
            System.out.println("Resposta diferente");
            return false;
        }

    }

    public boolean isTypeRacerEnabled() {
        return typeRacerEnabled;
    }

}
