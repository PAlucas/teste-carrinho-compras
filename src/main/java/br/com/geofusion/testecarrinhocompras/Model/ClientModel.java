package br.com.geofusion.testecarrinhocompras.Model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Client")
@SequenceGenerator(name="ClientSeq", sequenceName="ClientSeq", initialValue=1, allocationSize=10)
public class ClientModel implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ClientSeq")
    private long clientId;
    @Column(nullable = false, length = 255)
    private String nome;

    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
