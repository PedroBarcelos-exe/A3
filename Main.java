import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num;
        do{
            System.out.println("================================================");
            System.out.println("1. Cadastrar pessoa\n");
            System.out.println("2. Cadastrar veículo\n");
            System.out.println("3. Transferir veículo\n");
            System.out.println("4. Consultar veículo por placa\n");
            System.out.println("5. Consultar veículos por pessoa\n");
            System.out.println("6. Consulta histórico veículo\n");
            System.out.println("7. Quantidade de veículos por marca\n");
            System.out.println("8. Quantidade de veículos transformar por tempo\n");
            System.out.println("9. Consultar veículos com placa antiga\n");
            System.out.println("===============================================");
            System.out.println("Digite outro número para sair: ");
            num = sc.nextInt();
            switch (num) {
                case 1: System.out.println("clicou 1"); break;
                case 2: System.out.println("clicou 2"); break;
                case 3: System.out.println("clicou 3"); break;
                case 4: System.out.println("clicou 4"); break;
                case 5: System.out.println("clicou 5"); break;
                case 6: System.out.println("clicou 6"); break;
                case 7: System.out.println("clicou 7"); break;
                case 8: System.out.println("clicou 8"); break;
                case 9: System.out.println("clicou 9"); break;
                default: System.out.println("clicou sair"); break;
            }
        }while(num != 0);
        sc.close();
    }
    
}
