import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num;
        do{
            System.out.println("================================================");
            System.out.println("1. Cadastrar\n");
            System.out.println("2. Transferir veículo\n");
            System.out.println("3. Consultar\n");
            System.out.println("4. Quantidade de veículos por marca\n");
            System.out.println("5. Quantidade de veículos transformar por tempo\n");
            System.out.println("===============================================");
            System.out.println("Digite o número 0 para sair: ");
            num = sc.nextInt();
            switch (num) {
                case 1: 
                System.out.println("Cadastrar Pessoa");
                System.out.println("Cadastrar Veículo");
                break;
                case 2: System.out.println("Tranfira o Veículo");
                break;
                case 3: System.out.println("Mostrar Consulta"); 
                break;
                case 4: System.out.println("Mostrar Veículos por Marca"); 
                break;
                case 5: System.out.println("Mostrar Veículos por Tempo"); 
                break;
                default: System.out.println("clicou sair"); break;
            }
        }while(num != 0);
        sc.close();
    }
    
}
