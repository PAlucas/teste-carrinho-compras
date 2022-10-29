package br.com.geofusion.testecarrinhocompras.Model;
import java.io.Serializable;
import java.math.BigDecimal;

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
    @ManyToOne
    @JoinColumn(name ="code", nullable = false)
    private ProductModel codeProduto;
    @Column(nullable = false, length = 255)
    private int quantity;
    @Column(nullable = false, length = 255)
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name ="shopId", nullable = false)
    private ShoppingCartModel idShop;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductModel getCodeProduto() {
        return this.codeProduto;
    }

    public void setCodeProduto(ProductModel codeProduto) {
        this.codeProduto = codeProduto;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ShoppingCartModel getIdShop() {
        return this.idShop;
    }

    public void setIdShop(ShoppingCartModel idShop) {
        this.idShop = idShop;
    }


}
