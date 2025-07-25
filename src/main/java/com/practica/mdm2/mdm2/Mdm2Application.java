package com.practica.mdm2.mdm2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import etl.model.ProductoFinal;
import etl.service.ProductLoader;
import etl.service.ProductTransformer;
import etl.service.StagingLoader;

import java.util.List;

@SpringBootApplication
public class Mdm2Application {

    public static void main(String[] args) {
        SpringApplication.run(Mdm2Application.class, args);
    }

    // Usamos @Bean para crear un "bean" de tipo CommandLineRunner
    // Spring ejecutará este método automáticamente al arrancar.
    @Bean
    public CommandLineRunner runEtlProcess(StagingLoader stagingLoader, 
                                           ProductTransformer productTransformer, 
                                           ProductLoader productLoader) {
        // El método devuelve una expresión lambda, que es la implementación del método 'run'
        return args -> {
            System.out.println("==============================================");
            System.out.println("🚀 INICIANDO PROCESO ETL COMPLETO (desde Mdm2Application)...");
            System.out.println("==============================================");

            // --- FASE 1: STAGING ---
            System.out.println("\n[FASE 1/3] Ejecutando Staging Loader...");
            stagingLoader.cargarProveedorA();
            stagingLoader.cargarProveedorB();
            System.out.println("✅ Staging completado.");

            // --- FASE 2: TRANSFORMACIÓN ---
            System.out.println("\n[FASE 2/3] Ejecutando Product Transformer...");
            List<ProductoFinal> productosLimpios = productTransformer.transformarProductos();
            
            // --- FASE 3: CARGA FINAL ---
            System.out.println("\n[FASE 3/3] Ejecutando Product Loader...");
            productLoader.cargaDeDatos(productosLimpios);

            System.out.println("\n==============================================");
            System.out.println("🎉 PROCESO ETL FINALIZADO CON ÉXITO.");
            System.out.println("==============================================");
        };
    }
}