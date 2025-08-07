package com.practica.mdm2.mdm2.etl.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.mdm2.mdm2.etl.model.ProductoProveedorB;
@Service
public class StagingLoader {
    String url = "jdbc:postgresql://localhost:5432/mdm2";
    String user = "postgres";
    String password = "admin";

   public void cargarProveedorA() {
    String csvFilePath = "C:\\temp-data\\proveedor_a.csv";
    System.out.println("Iniciando carga de proveedor_a.csv...");

    String insertSQL = "INSERT INTO proveedor_a (id_producto, nombre_articulo, precio_usd, categoria) VALUES (?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = conn.prepareStatement(insertSQL);
         BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

        String line;
        boolean isFirstLine = true;
        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {  // Saltar la cabecera
                isFirstLine = false;
                continue;
            }

            String[] values = line.split(",", -1);

            pstmt.setInt(1, Integer.parseInt(values[0].trim()));
            pstmt.setString(2, values[1].trim());
            pstmt.setDouble(3, Double.parseDouble(values[2].trim()));
            pstmt.setString(4, values[3].trim());

            pstmt.addBatch();
        }

        pstmt.executeBatch();
        System.out.println("-> Carga de proveedor_a.csv completada con éxito.");

    } catch (Exception e) {
        System.err.println("❌ Error al cargar proveedor_a.csv:");
        e.printStackTrace();
    }
}



    public void cargarProveedorB() {
        String jsonFilePath = "C:\\temp-data\\proveedor_b.csv";

        ObjectMapper miTraductor = new ObjectMapper();
        List<ProductoProveedorB> listaDeProductos = new ArrayList<>();

        try {
            listaDeProductos = miTraductor.readValue(
                new File(jsonFilePath),
                new TypeReference<List<ProductoProveedorB>>() {}
            );
            System.out.println("-> Se leyeron " + listaDeProductos.size() + " productos del JSON.");
        } catch (IOException e) {
            System.err.println("❌ Error al leer el JSON del proveedor B:");
            e.printStackTrace();
            return;
        }

        String sqlInsert = "INSERT INTO proveedor_b (codigo_producto, nombre_producto, precio_valor, precio_moneda, ref_categoria) VALUES (?,?,?,?,?)";
        System.out.println("Iniciando la carga de proveedor_b.json a la base de datos...");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            for (ProductoProveedorB producto : listaDeProductos) {
                    pstmt.setString(1, producto.getProductCode());
                    pstmt.setString(2, producto.getProductName());
                    pstmt.setDouble(3, producto.getPriceData().getValue());
                    pstmt.setString(4, producto.getPriceData().getCurrency());
                    pstmt.setString(5, producto.getCategoryRef());
                    pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("-> Carga de datos del Proveedor B completada con éxito.");

            }catch (Exception e) {
            System.err.println("❌ Error al insertar datos del Proveedor B:");
            e.printStackTrace();
        }
    }
}

