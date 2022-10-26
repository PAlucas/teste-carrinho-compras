package br.com.geofusion.testecarrinhocompras.Model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ShoppingCart")
@SequenceGenerator(name="ShoppingCardSeq", sequenceName="ShoppingCardSeq", initialValue=1, allocationSize=10)
public class ShoppingCartModel implements Serializable{
    //Serialization to grant safety of an object state
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ShoppingCardSeq")
    private long shopId;


    public long getShopId() {
        return this.shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
}
