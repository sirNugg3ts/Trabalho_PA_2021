package pt.isec.a2018019825.jogo.Utils;

import java.util.Scanner;

public class Utils {

    static Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    public static int escolheOpcao(String... opcoes){
        int opcao;
        do{
            for(int i=0;i<opcoes.length-1;i++){
                System.out.printf("%3d - %s\n",i+1,opcoes[i]);
            }
            System.out.printf("\n%3d - %s\n",0,opcoes[opcoes.length-1]);
            opcao = pedeInteiro("\n> ");
        }while(opcao<0 || opcao>opcoes.length);
        return opcao;
    }

    public static int pedeInteiro(String pergunta){
        System.out.println(pergunta);
        while (!sc.hasNextInt())
            sc.next();
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}
