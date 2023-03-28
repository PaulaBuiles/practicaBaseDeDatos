package org.example;

import org.example.model.Product;
import org.example.repository.Repository;
import org.example.repository.impl.ProductRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        try (Connection conn = ConexionBD.getInstance()) {
            Repository<Product> repository = new ProductRepositoryImpl();
            listProducts(repository);
            getProductById(repository);
            //addProduct(repository);
            //updateProduct(repository);
            deleteProduct(repository);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getProductById(Repository<Product> repository) {
        System.out.println("GET PRODUCT BY ID: -------------------");
        System.out.println(repository.byId(Long.valueOf(1)));
    }

    private static void listProducts(Repository<Product> repository) {
        System.out.println("LIST PRODUCTS:-------------------");
        repository.list().forEach(System.out::println);
    }
    private static void deleteProduct(Repository<Product> repository) {
        System.out.println("DELETE PRODUCT:-------------------");
        repository.delete(Long.valueOf(2));
    }
}
