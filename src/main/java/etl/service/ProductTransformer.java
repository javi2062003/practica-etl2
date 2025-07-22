package etl.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etl.model.ProductoFinal;


public class ProductTransformer {
    String url = "jdbc:postgresql://localhost:5432/mdm";
    String user = "postgres";
    String password = "admin";

 // COMENZAMOS CON LA PRIMERA FUNCIÓN PARA LEER LOS DATOS DE LAS TABLAS DE ORIGEN Y CREANDO
 // OBJETOS CON ESOS VALORES PARA TRANSFORMARLOS

    public List<ProductoFinal> transformarProductos(){
         List<Map<String, Object>> productosBrutos = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password)) { 

            //CONSULTA PARA OBTENER LOS DATOS DE LAS TABLAS     

                String sqlA = "SELECT * FROM proveedor_a ";
                try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlA)) {

            //ACCIÓN PARA LEER LOS VALORES DE CADA LINEA OBTENIDA DE LA CONSULTA Y ALMACENARLOS EN UN ARRAY
                while (rs.next()) {
                    Map<String, Object> productoA = new HashMap<>();
                    productoA.put("id_original", rs.getInt("id_producto"));
                    productoA.put("nombre_bruto", rs.getString(("nombre_articulo")));
                    productoA.put("precio_bruto", rs.getDouble("precio_usd"));
                    productoA.put("categoria_bruta", rs.getString(("categoria")));
                    productoA.put("fuente", "PROVA");

                    productosBrutos.add(productoA);
                        }
                    }
                String sqlB = "SELECT * FROM proveedor_b ";
                try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlB)) {

                    while (rs.next()) {
                        Map<String, Object> productoB = new HashMap<>();
                        productoB.put("id_original", rs.getInt("codigo_producto"));
                        productoB.put("nombre_bruto", rs.getString(("nombre_producto")));
                        productoB.put("precio_bruto", rs.getDouble("precio_moneda"));
                        productoB.put("categoria_bruta", rs.getString(("ref_categoria")));
                        productoB.put("fuente", "PROVB");
                    }
                }
        } catch (Exception e) {
            System.err.println("❌ Error al obtener datos de proveedor_a");
            e.printStackTrace();
        }

    
        List<ProductoFinal> productosFinales = new ArrayList<>();
        for (Map<String, Object> bruto : productosBrutos) {
            String fuente = (String) bruto.get("fuente");
            String idOriginal = bruto.get("id_original").toString();
            String sku = fuente + " - " + idOriginal;
            
            String nombreLimpio = ((String)
            bruto.get("nombre_bruto")).trim().toLowerCase();

            double precioEnEur;
            if(fuente.equals("PROVB") && "USD".equals(bruto.get("moneda_bruta"))) {
                precioEnEur = (Double) bruto.get("precio_bruto") * 0.92;
            } else if (fuente.equals("PROVA")) {
                precioEnEur = (Double) bruto.get("precio_bruto") * 0.92;

            } else {
                precioEnEur = (Double) bruto.get("precio_bruto");

            }

            int idCategoria = 1;

            ProductoFinal productoLimpio = new ProductoFinal(idCategoria, nombreLimpio, nombreLimpio, idCategoria, idCategoria, nombreLimpio, null);

            productoLimpio.setSku(sku);
            productoLimpio.setNombre(nombreLimpio);
            productoLimpio.setPrecio(precioEnEur);
            productoLimpio.setId_categoria(idCategoria);
            productoLimpio.setFuenteProveedor(fuente);

            productosFinales.add(productoLimpio);

        }
        return productosFinales;
    }        
}

