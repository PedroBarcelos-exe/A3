import java.util.Random; 


public class Veiculo {
    private int id; 
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private String placaAtual; 

    public Veiculo(String marca, String modelo, int ano, String cor) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    
    public Veiculo(int id, String marca, String modelo, int ano, String cor) {
        // Chama o construtor anterior para inicializar os atributos básicos.
        this(marca, modelo, ano, cor);
        this.id = id; // Define o ID.
    }

    public Veiculo(int id, String marca, String modelo, int ano, String cor, String placaAtual) {
        this(id, marca, modelo, ano, cor);
        this.placaAtual = placaAtual; 
    }

  
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public String getCor() { return cor; }
    public String getPlacaAtual() { return placaAtual; }

   
    public void setId(int id) { this.id = id; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
    public void setCor(String cor) { this.cor = cor; }
    public void setPlacaAtual(String placaAtual) { this.placaAtual = placaAtual; }

   
    public static String generateMercosulPlate() {
        Random random = new Random();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitos = "0123456789";
        StringBuilder placa = new StringBuilder(); 

        for (int i = 0; i < 3; i++) {
            placa.append(letras.charAt(random.nextInt(letras.length())));
        }
        placa.append(digitos.charAt(random.nextInt(digitos.length())));
        placa.append(letras.charAt(random.nextInt(letras.length())));
        for (int i = 0; i < 2; i++) {
            placa.append(digitos.charAt(random.nextInt(digitos.length())));
        }
        return placa.toString(); 
    }

    
    public static String convertOldPlateToMercosul(String oldPlate) {
        if (oldPlate == null || !oldPlate.matches("[A-Z]{3}-\\d{4}")) {
            throw new IllegalArgumentException("Placa antiga inválida: " + oldPlate + ". Formato esperado: LLL-NNNN.");
        }
        String[] partes = oldPlate.split("-"); 
        String letras = partes[0]; 
        String numeros = partes[1]; 

       
        char segundoDigitoChar = numeros.charAt(1); 
        int valorNumerico = Character.getNumericValue(segundoDigitoChar); 
        char novaLetra = (char) ('A' + valorNumerico); 

       
        return letras + numeros.charAt(0) + novaLetra + numeros.substring(2);
    }

    public static boolean isOldPlateFormat(String placa) {
        return placa != null && placa.matches("[A-Z]{3}-\\d{4}");
    }

    
    public static boolean isMercosulPlateFormat(String placa) {
        return placa != null && placa.matches("[A-Z]{3}\\d[A-Z]\\d{2}");
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Ano: " + ano + ", Cor: " + cor + ", Placa: " + (placaAtual != null ? placaAtual : "N/A");
    }
}