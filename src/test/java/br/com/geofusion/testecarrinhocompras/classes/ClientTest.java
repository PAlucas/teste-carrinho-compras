package br.com.geofusion.testecarrinhocompras.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientTest {

    /**
     * Teste para ver se quando colocado o nome do
     * cliente ele vai retornar o Map correto
     */
    @Test
    public void testJsonCliente(){
        Client client = new Client("Cliente Teste");

        String stringFinal = "{Nome=Cliente Teste}";

        assertEquals(stringFinal, client.json().toString());
    }

    /**
     * Teste para ver se quando colocado o nome do
     * cliente ele vai retornar o json de novo nome
     */
    @Test
    public void testTrocarCliente(){
        Client client = new Client("Cliente Teste");

        String stringFinal = "{Novo nome=Cliente Teste}";
        assertEquals(stringFinal, client.nomeNovoJson().toString());
    }

    /**
     * Teste para ver se quando colocado o nome do
     * cliente ele vai retornar o json de nome deletado
     */
    @Test
    public void testDeleteCliente(){
        Client client = new Client("Cliente Teste");

        String stringFinal = "{Cliente Deletado=Cliente Teste}";
        assertEquals(stringFinal, client.nomeDeletadoJson().toString());
    }

}
