/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.ConexionDB;
import model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // 1. MÉTODO PARA REGISTRAR UN NUEVO PRODUCTO
    public boolean registrar(Producto prod) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio, stock, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getCategoria());
            ps.setDouble(4, prod.getPrecio());
            ps.setInt(5, prod.getStock());
            ps.setString(6, prod.getEstado());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    // 2. MÉTODO PARA LISTAR TODOS LOS PRODUCTOS
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setCodigo(rs.getString("codigo"));
                prod.setNombre(rs.getString("nombre"));
                prod.setCategoria(rs.getString("categoria"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setEstado(rs.getString("estado"));
                
                lista.add(prod);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // 3. MÉTODO PARA MODIFICAR UN PRODUCTO
    public boolean actualizar(Producto prod) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, categoria=?, precio=?, stock=?, estado=? WHERE id=?";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getCategoria());
            ps.setDouble(4, prod.getPrecio());
            ps.setInt(5, prod.getStock());
            ps.setString(6, prod.getEstado());
            ps.setInt(7, prod.getId());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // 4. MÉTODO PARA ELIMINAR UN PRODUCTO POR ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    public Producto obtenerPorCodigoOId(String criterio) {
    String sql = "SELECT * FROM producto WHERE codigo = ? OR id = ?";
    Producto p = null;
    
    try (Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

           ps.setString(1, criterio);

           // Si el criterio es un número, también busca por ID
           try {
               ps.setInt(2, Integer.parseInt(criterio));
           } catch (NumberFormatException e) {
               ps.setInt(2, -1); // Si no es un entero, asigna un ID imposible
           }

           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   p = new Producto();
                   p.setId(rs.getInt("id"));
                   p.setCodigo(rs.getString("codigo"));
                   p.setNombre(rs.getString("nombre"));
                   p.setCategoria(rs.getString("categoria"));
                   p.setPrecio(rs.getDouble("precio"));
                   p.setStock(rs.getInt("stock"));
                   p.setEstado(rs.getString("estado"));
               }
           }
       } catch (Exception e) {
           System.err.println("Error al consultar producto: " + e.getMessage());
       }

       return p; // Devuelve el Objeto Producto o null si no se encontró
   }
    
}