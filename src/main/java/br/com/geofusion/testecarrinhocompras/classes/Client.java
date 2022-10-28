package br.com.geofusion.testecarrinhocompras.classes;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Método para criar um json retorna nome do cliente
     * @param nome
     */
    public Map<String, String> json() {
        HashMap<String, String> json = new HashMap<>();
        json.put("Nome", this.nome);
        return json;
    }
}
