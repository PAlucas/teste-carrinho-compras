package br.com.geofusion.testecarrinhocompras.Model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@SequenceGenerator(name="ProductSeq", sequenceName="ProductSeq", initialValue=1, allocationSize=10)
public class ProductModel implements Serializable{
    //Serialization to grant safety of an object state
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ProductSeq")
    private long code;
    @Column(nullable = false, length = 255)
    private String description;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
