package com.practica.mdm2.mdm2.etl.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practica.mdm2.mdm2.etl.model.ProductoFinal;
@Service
public class ProductLoader {
    String url = "jdbc:postgresql://localhost:5432/mdm2";
    String user = "postgres";
    String password = "admin";

    public void cargaDeDatos(List<ProductoFinal> productos) {

    String sqlTruncate = "TRUNCATE TABLE catalogo_productos RESTART IDENTITY;";
    try (Connection conn = DriverManager.getConnection(url, user, password);
         Statement stmt = conn.createStatement()) {

        stmt.execute(sqlTruncate);
        System.out.println("-> Tabla catalogo_productos vaciada con éxito.");

    } catch (Exception e) {
        System.err.println("❌ Error al vaciar la tabla catalogo_productos:");
        e.printStackTrace();
        return;
    }

    String sqlInsert = "INSERT INTO catalogo_productos (sku, nombre_normalizado, precio_eur, id_categoria, fuente_proveedor) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

        for (ProductoFinal producto : productos) {
            // ¡Los setters CORRECTOS!
            pstmt.setString(1, producto.getSku());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getId_categoria());
            pstmt.setString(5, producto.getFuenteProveedor());
            
            pstmt.addBatch();
        }

        pstmt.executeBatch();
        System.out.println("-> Se han insertado " + productos.size() + " productos en la tabla final.");

    } catch (Exception e) {
        System.err.println("❌ Error durante la carga de datos en batch:");
        e.printStackTrace();
    }
}
}
