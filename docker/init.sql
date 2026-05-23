-- ==========================================
-- ESQUEMA: usuarios
-- ==========================================
CREATE SCHEMA IF NOT EXISTS usuarios;

CREATE TABLE IF NOT EXISTS usuarios.usuario (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS usuarios.restriccion (
    id          BIGSERIAL PRIMARY KEY,
    usuario_id  BIGINT NOT NULL REFERENCES usuarios.usuario(id),
    ingrediente VARCHAR(100) NOT NULL,
    tipo        VARCHAR(30) NOT NULL CHECK (tipo IN ('ALERGIA','INTOLERANCIA','PREFERENCIA'))
);

-- ==========================================
-- ESQUEMA: productos
-- ==========================================
CREATE SCHEMA IF NOT EXISTS productos;

CREATE TABLE IF NOT EXISTS productos.producto (
    id              BIGSERIAL PRIMARY KEY,
    nombre          VARCHAR(150) NOT NULL,
    marca           VARCHAR(100),
    categoria       VARCHAR(80),
    codigo_barras   VARCHAR(50) NOT NULL UNIQUE,
    descripcion     TEXT
);

CREATE TABLE IF NOT EXISTS productos.ingrediente (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    tipo        VARCHAR(80),
    descripcion TEXT,
    nivel_riesgo VARCHAR(20) NOT NULL CHECK (nivel_riesgo IN ('BAJO','MEDIO','ALTO'))
);

CREATE TABLE IF NOT EXISTS productos.producto_ingrediente (
    producto_id     BIGINT NOT NULL REFERENCES productos.producto(id),
    ingrediente_id  BIGINT NOT NULL REFERENCES productos.ingrediente(id),
    concentracion   VARCHAR(50),
    PRIMARY KEY (producto_id, ingrediente_id)
);

-- ==========================================
-- ESQUEMA: analisis
-- ==========================================
CREATE SCHEMA IF NOT EXISTS analisis;

CREATE TABLE IF NOT EXISTS analisis.analisis (
    id              BIGSERIAL PRIMARY KEY,
    codigo_barras   VARCHAR(50) NOT NULL,
    puntuacion      INTEGER NOT NULL CHECK (puntuacion BETWEEN 0 AND 100),
    clasificacion   VARCHAR(30) NOT NULL CHECK (clasificacion IN (
                        'BUENO','RIESGO_BAJO','REGULAR','RIESGO_ALTO','MALO'
                    )),
    detalle         TEXT,
    fecha           TIMESTAMP DEFAULT NOW()
);

-- ==========================================
-- ESQUEMA: recomendaciones
-- ==========================================
CREATE SCHEMA IF NOT EXISTS recomendaciones;

CREATE TABLE IF NOT EXISTS recomendaciones.recomendacion (
    id              BIGSERIAL PRIMARY KEY,
    usuario_id      BIGINT NOT NULL,
    codigo_barras   VARCHAR(50) NOT NULL,
    resultado       VARCHAR(30) NOT NULL CHECK (resultado IN (
                        'APTO','NO_APTO','USAR_CON_PRECAUCION'
                    )),
    motivo          TEXT,
    fecha           TIMESTAMP DEFAULT NOW()
);

-- ==========================================
-- DATOS DE PRUEBA
-- ==========================================
INSERT INTO productos.ingrediente (nombre, tipo, nivel_riesgo) VALUES
('Agua', 'Solvente', 'BAJO'),
('Glicerina', 'Humectante', 'BAJO'),
('Parabenos', 'Conservante', 'ALTO'),
('Sulfato de Sodio', 'Surfactante', 'MEDIO'),
('Vitamina E', 'Antioxidante', 'BAJO'),
('Formaldehido', 'Conservante', 'ALTO'),
('Alcohol Cetílico', 'Emoliente', 'BAJO'),
('Fragancia Sintética', 'Perfume', 'MEDIO');

INSERT INTO productos.producto (nombre, marca, categoria, codigo_barras, descripcion) VALUES
('Shampoo Suave', 'BrandA', 'Cabello', '7501234567890', 'Shampoo para cabello normal'),
('Crema Hidratante', 'BrandB', 'Piel', '7509876543210', 'Crema facial hidratante'),
('Gel de Ducha', 'BrandC', 'Cuerpo', '7501122334455', 'Gel refrescante'),
('Acondicionador', 'BrandA', 'Cabello', '7505544332211', 'Acondicionador reparador'),
('Protector Solar', 'BrandD', 'Piel', '7506677889900', 'SPF 50');
