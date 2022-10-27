package br.com.geofusion.testecarrinhocompras.classes;

public class Client {
    private String nome;

    /**
     * Construtor da classe Item.
     *
     * @param nome
     */
    public Client(String nome) {
        this.nome = nome;
    }

    /**
     * Método get retorna o nome do cliente
     * @return String
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Método set para mudar nome de cliente
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
