package etl.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import etl.model.ProductoProveedorB;

public class StagingLoader {
    String url = "jdbc:postgresql://localhost:5432/mdm";
    String user = "postgres";
    String password = "admin";

    public void cargarProveedorA() {
        String csvFilePath = "C:\\temp-data\\proveedor_a.csv";
        String sql = String.format("COPY proveedor_a FROM '%s' WITH (FORMAT CSV, HEADER)", csvFilePath);
        System.out.println("Iniciando carga de proveedor_a.csv...");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("-> Carga de proveedor_a.csv completada con éxito.");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar proveedor_a.csv:");
            e.printStackTrace();
        }
    }

    public void cargarProveedorB() {
        String jsonFilePath = "C:\\temp-data\\proveedor_b.json";

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

