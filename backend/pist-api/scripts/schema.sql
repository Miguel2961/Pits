-- Schema de la base de datos Pits (PostgreSQL).
-- Ejecutar UNA vez antes del seed si las tablas no existen (por ejemplo en una base nueva).
-- Cómo ejecutar: psql -U postgres -h localhost -d pits -f scripts/schema.sql
-- O en pgAdmin: Query Tool sobre la base pits → pegar y ejecutar.
--
-- Si en cambio arrancas primero la app Spring Boot (ddl-auto=update), Hibernate crea las tablas
-- y solo necesitas ejecutar scripts/seed.sql.

-- 1. Especialidad
CREATE TABLE IF NOT EXISTS especialidad (
  id_especialidad BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 2. Mecánico
CREATE TABLE IF NOT EXISTS mecanico (
  id_mecanico BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  contacto VARCHAR(100),
  certificacion VARCHAR(255),
  edad INTEGER,
  experiencia INTEGER,
  ciudad VARCHAR(100),
  foto VARCHAR(500),
  disponible BOOLEAN NOT NULL DEFAULT true,
  rating DOUBLE PRECISION,
  cantidad_valoraciones INTEGER NOT NULL DEFAULT 0
);

-- 3. Tabla enlace mecánico–especialidad (N:N)
CREATE TABLE IF NOT EXISTS mecanico_especialidad (
  id_mecanico BIGINT NOT NULL REFERENCES mecanico(id_mecanico) ON DELETE CASCADE,
  id_especialidad BIGINT NOT NULL REFERENCES especialidad(id_especialidad) ON DELETE CASCADE,
  PRIMARY KEY (id_mecanico, id_especialidad)
);

-- 4. Cliente
CREATE TABLE IF NOT EXISTS cliente (
  id_cliente BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  contraseña VARCHAR(255) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  foto VARCHAR(500)
);

-- 5. Vehículo
CREATE TABLE IF NOT EXISTS vehiculo (
  id_vehiculo BIGSERIAL PRIMARY KEY,
  id_cliente BIGINT NOT NULL REFERENCES cliente(id_cliente) ON DELETE CASCADE,
  carburacion VARCHAR(100),
  placa VARCHAR(20) NOT NULL,
  modelo VARCHAR(100),
  anio INTEGER,
  tecno_mecanica VARCHAR(100),
  tipo VARCHAR(50),
  color VARCHAR(50)
);

-- 6. Servicio
CREATE TABLE IF NOT EXISTS servicio (
  id_servicio BIGSERIAL PRIMARY KEY,
  id_vehiculo BIGINT NOT NULL REFERENCES vehiculo(id_vehiculo) ON DELETE CASCADE,
  id_mecanico BIGINT NOT NULL REFERENCES mecanico(id_mecanico) ON DELETE RESTRICT,
  servicio_actual VARCHAR(255),
  reporte VARCHAR(1000)
);

-- 7. Historial (reportes por servicio)
CREATE TABLE IF NOT EXISTS historial (
  id_reporte BIGSERIAL PRIMARY KEY,
  id_servicio BIGINT NOT NULL REFERENCES servicio(id_servicio) ON DELETE CASCADE,
  id_cliente BIGINT NOT NULL REFERENCES cliente(id_cliente) ON DELETE CASCADE,
  informacion_servicio VARCHAR(2000)
);
