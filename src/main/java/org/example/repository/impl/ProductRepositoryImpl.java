package org.example.repository.impl;

import org.example.ConexionBD;
import org.example.model.Categorie;
import org.example.model.Product;
import org.example.repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {


    @Override
    public List<Product> list() {
        List<Product> productoList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT p.*,c.nombre_categorie as name from products as p inner join categories as c ON (p.categorie_id=c.id)")) {
            while (resultSet.next()) {
                Product producto = createProduct(resultSet);
                productoList.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return productoList;
    }

    @Override
    public Product byId(Long id) {
        Product producto = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM products WHERE id =?")) {
                preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            producto = createProduct(resultSet);
        }
        resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return producto;
    }

    @Override
    public void save(Product product) throws SQLException {
        String sql;
        if(product.getId() != null && product.getId()>0){
            sql = "UPDATE products SET name=?, price=?, categorie_id=? WHERE id=?";
        }else{
            sql ="INSERT INTO products(name, price, categorie_id, date_register) VALUES(?,?,?,?)";
        }
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getCategorie().getId());

            if(product.getId() != null && product.getId()>0){
                preparedStatement.setLong(4,product.getId());
            }else{
                preparedStatement.setDate(4,Date.valueOf(product.getDate_register()));
            }
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void delete(Long id) {
        Product producto = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE from products WHERE id=?")) {
            preparedStatement.setLong(1, id);
            int resultSet = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection getConnection() throws SQLException {
        return ConexionBD.getInstance();
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product producto = new Product();
        producto.setId(resultSet.getLong("id"));
        producto.setName(resultSet.getString("nombre"));
        producto.setPrice(resultSet.getDouble("precio"));
        producto.setDate_register(resultSet.getDate("fecha_registro").toLocalDate());

        Categorie categorie = new Categorie();
        categorie.setId(resultSet.getLong("categorie_id"));
        categorie.setName(resultSet.getString("nombre"));
        producto.setCategorie(categorie);
        return producto;
    }

}
