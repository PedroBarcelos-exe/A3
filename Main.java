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
            System.out.println("Digite 0 para sair: ");
            num = sc.nextInt();
        }while(num != 0);
    }
}
