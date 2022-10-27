package br.com.geofusion.testecarrinhocompras.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public List<ProductModel> findAll(){
        return productRepository.findAll();
    }

    public boolean existsByCode(long code) {
        return productRepository.existsByCode(code);
    }

    public Optional<ProductModel> findByCode(long code) {
        return productRepository.findById(code);
    }

    @Transactional
    public String delete(ProductModel productModel) {
        String descricao = productModel.getDescription();
        productRepository.delete(productModel);
        return descricao;
    }
}
