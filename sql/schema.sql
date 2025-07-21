CREATE TABLE proveedor_a (
    id_producto SERIAL primary key,
    nombre_articulo VARCHAR(255),
    precio_usd NUMERIC(10, 2),
    categoria VARCHAR(255)
);

CREATE TABLE proveedor_b (
    codigo_producto VARCHAR(50) primary key,
    nombre_producto VARCHAR(255),
    precio_valor NUMERIC(10, 2),
    precio_moneda VARCHAR(3),
    ref_categoria VARCHAR(100)
);

CREATE TABLE categorias_validas (
    id_categoria SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) UNIQUE NOT NULL
);



INSERT INTO categorias_validas (nombre_categoria) VALUES
('Electrónica'),
('Informática'),
('Hogar'),
('Sin Categoría'); 

--Una vez creadas las tablas anteriores, crear esta última, y no antes, pues nos dará
-- un error si no estan creadas previamente.

CREATE TABLE catalogo_productos (
    id_producto SERIAL PRIMARY KEY,                   
    sku VARCHAR(50) UNIQUE NOT NULL,                   
    nombre_normalizado VARCHAR(255) NOT NULL,         
    precio_eur NUMERIC(10, 2) NOT NULL,                 
    id_categoria INTEGER REFERENCES categorias_validas(id_categoria), 
    fuente_proveedor VARCHAR(50),                      
    fecha_actualizacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP 
);

