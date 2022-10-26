package br.com.geofusion.testecarrinhocompras.Model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Client")
@SequenceGenerator(name="ClientSeq", sequenceName="ClientSeq", initialValue=1, allocationSize=10)
public class ClientModel implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ClientSeq")
    private long code;
    @Column(nullable = false, length = 255)
    private String description;

    public long getCode() {
        return this.code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
