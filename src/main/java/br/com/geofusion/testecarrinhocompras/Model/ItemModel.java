package br.com.geofusion.testecarrinhocompras.Model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Item")
@SequenceGenerator(name="ItemSeq", sequenceName="ItemSeq", initialValue=1, allocationSize=10)
public class ItemModel implements Serializable{
    //Serialization to grant safety of an object state
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ItemSeq")
    private long id;
    @Column(nullable = false, length = 255)
    private long codeProduto;
    @Column(nullable = false, length = 255)
    private String quantity;
    @Column(nullable = false, length = 255)
    private double unitPrice;
    @ManyToOne
    @JoinColumn(name ="shopId")
    private ShoppingCartModel idShop;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCodeProduto() {
        return this.codeProduto;
    }

    public void setCodeProduto(long codeProduto) {
        this.codeProduto = codeProduto;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ShoppingCartModel getIdShop() {
        return this.idShop;
    }

    public void setIdShop(ShoppingCartModel idShop) {
        this.idShop = idShop;
    }


}
