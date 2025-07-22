package etl.model;

import java.security.Timestamp;

public class ProductoFinal {
    private Integer id;
    private String sku;
    private String nombre;
    private Double precio;
    private Integer id_categoria;
    private String fuenteProveedor;
    private Timestamp fechaActualizacion;
    
    public ProductoFinal(Integer id, String sku, String nombre, Integer precio, Integer id_categoria,
            String fuenteProveedor, Timestamp fechaActualizacion) {
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.id_categoria = id_categoria;
        this.fuenteProveedor = fuenteProveedor;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getFuenteProveedor() {
        return fuenteProveedor;
    }

    public void setFuenteProveedor(String fuenteProveedor) {
        this.fuenteProveedor = fuenteProveedor;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
}
