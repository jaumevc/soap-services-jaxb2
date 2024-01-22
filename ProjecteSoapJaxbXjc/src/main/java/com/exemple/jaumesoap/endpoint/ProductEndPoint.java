package com.exemple.jaumesoap.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.exemple.jaumesoap.converter.ProductConverter;
import com.exemple.jaumesoap.model.ProductModel;
import com.exemple.jaumesoap.repository.ProductRepository;
import com.exemple.jaumesoap.generated.GetProductRequest;
import com.exemple.jaumesoap.generated.GetProductResponse;
import com.exemple.jaumesoap.generated.GetProductsRequest;
import com.exemple.jaumesoap.generated.GetProductsResponse;
import com.exemple.jaumesoap.generated.PostProductRequest;
import com.exemple.jaumesoap.generated.PostProductResponse;
import com.exemple.jaumesoap.generated.Product;

@Endpoint
public class ProductEndPoint {

	private static final String NAMESPACE_URI = "http://www.jaume.com/jaumesoap/generated";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        ProductModel productModel = productRepository.findByName(request.getName());
        response.setProduct(productConverter.convertProductModelToProduct(productModel));
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        List<ProductModel> productModels = productRepository.findAll();
        List<Product> products = productConverter.convertProductModelsToProducts(productModels);
        response.getProducts().addAll(products);
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postProductRequest")
    @ResponsePayload
    public PostProductResponse postProducts(@RequestPayload PostProductRequest request) {
        PostProductResponse response = new PostProductResponse();
        ProductModel productModel = productConverter.convertProductToProductModel(request.getProduct());
        Product product = productConverter.convertProductModelToProduct(productRepository.save(productModel));
        response.setProduct(product);
        return response;
    }
}
