-- Seed data: especialidades y mecánicos (mock para MVP - vista cliente).
-- Las tablas deben existir antes de ejecutar este script. Opciones:
--   A) Base nueva: ejecutar primero scripts/schema.sql y luego este seed.sql.
--   B) O arrancar una vez la app Spring Boot (ddl-auto=update crea las tablas) y luego este seed.sql.
-- Cómo ejecutar: psql -U postgres -h localhost -d pits -f scripts/seed.sql
-- O en pgAdmin: Query Tool sobre la base pits → pegar y ejecutar.

-- 1. Especialidades (IDs fijos para enlazar con mecánicos)
INSERT INTO especialidad (id_especialidad, nombre) VALUES
  (1, 'Motores'),
  (2, 'Frenos'),
  (3, 'Electricidad automotriz'),
  (4, 'Suspensión'),
  (5, 'Transmisión'),
  (6, 'Aire acondicionado automotriz'),
  (7, 'Diagnóstico electrónico')
ON CONFLICT (id_especialidad) DO NOTHING;

SELECT setval(pg_get_serial_sequence('especialidad', 'id_especialidad'), (SELECT COALESCE(MAX(id_especialidad), 1) FROM especialidad));

-- 2. Columna foto en mecanico (por si la tabla ya existía sin ella)
ALTER TABLE mecanico ADD COLUMN IF NOT EXISTS foto VARCHAR(500);

-- 3. Mecánicos (32 registros con fotos mock desde pravatar.cc)
INSERT INTO mecanico (id_mecanico, nombre, contacto, certificacion, edad, experiencia, ciudad, foto, disponible, rating, cantidad_valoraciones) VALUES
  (1, 'Carlos Gómez', '3001111111', NULL, 35, 10, 'Medellín', 'https://i.pravatar.cc/300?u=pits-1', true, 4.5, 12),
  (2, 'Laura Díaz', '3001111112', NULL, 29, 6, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-2', true, 4.8, 9),
  (3, 'Andrés López', '3001111113', NULL, 41, 15, 'Cali', 'https://i.pravatar.cc/300?u=pits-3', false, 4.2, 18),
  (4, 'Juan Pérez', '3001111114', NULL, 38, 12, 'Medellín', 'https://i.pravatar.cc/300?u=pits-4', true, 4.6, 14),
  (5, 'Miguel Torres', '3001111115', NULL, 33, 8, 'Barranquilla', 'https://i.pravatar.cc/300?u=pits-5', true, 3.9, 7),
  (6, 'Daniel Rojas', '3001111116', NULL, 45, 20, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-6', false, 4.7, 22),
  (7, 'Felipe Vargas', '3001111117', NULL, 31, 7, 'Cali', 'https://i.pravatar.cc/300?u=pits-7', true, 4.1, 5),
  (8, 'Luis Ramírez', '3001111118', NULL, 40, 14, 'Medellín', 'https://i.pravatar.cc/300?u=pits-8', true, 4.4, 16),
  (9, 'Sebastián Castro', '3001111119', NULL, 27, 5, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-9', true, NULL, 0),
  (10, 'Oscar Martínez', '3001111120', NULL, 36, 11, 'Cali', 'https://i.pravatar.cc/300?u=pits-10', false, 4.3, 10),
  (11, 'David Hernández', '3001111121', NULL, 34, 9, 'Medellín', 'https://i.pravatar.cc/300?u=pits-11', true, 4.0, 8),
  (12, 'Ricardo Gómez', '3001111122', NULL, 48, 22, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-12', false, 4.9, 25),
  (13, 'Camilo Restrepo', '3001111123', NULL, 30, 6, 'Medellín', 'https://i.pravatar.cc/300?u=pits-13', true, 3.8, 4),
  (14, 'Jorge Sánchez', '3001111124', NULL, 42, 16, 'Barranquilla', 'https://i.pravatar.cc/300?u=pits-14', true, 4.5, 13),
  (15, 'Diego Molina', '3001111125', NULL, 39, 13, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-15', true, 4.6, 17),
  (16, 'Pedro Salazar', '3001111126', NULL, 37, 12, 'Cali', 'https://i.pravatar.cc/300?u=pits-16', false, 4.2, 11),
  (17, 'Esteban Cárdenas', '3001111127', NULL, 32, 7, 'Medellín', 'https://i.pravatar.cc/300?u=pits-17', true, NULL, 0),
  (18, 'Santiago Ruiz', '3001111128', NULL, 28, 5, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-18', true, 3.7, 3),
  (19, 'Alejandro Moreno', '3001111129', NULL, 44, 18, 'Cali', 'https://i.pravatar.cc/300?u=pits-19', false, 4.8, 20),
  (20, 'Cristian Delgado', '3001111130', NULL, 33, 9, 'Medellín', 'https://i.pravatar.cc/300?u=pits-20', true, 4.1, 6),
  (21, 'Mauricio Giraldo', '3001111131', NULL, 41, 15, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-21', true, 4.4, 15),
  (22, 'Iván Ospina', '3001111132', NULL, 36, 10, 'Cali', 'https://i.pravatar.cc/300?u=pits-22', false, 4.0, 9),
  (23, 'Henry López', '3001111133', NULL, 47, 21, 'Medellín', 'https://i.pravatar.cc/300?u=pits-23', true, 4.7, 23),
  (24, 'Fernando Arias', '3001111134', NULL, 38, 13, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-24', true, 4.3, 11),
  (25, 'Wilson Mejía', '3001111135', NULL, 35, 10, 'Cali', 'https://i.pravatar.cc/300?u=pits-25', true, 4.5, 8),
  (26, 'Raúl Patiño', '3001111136', NULL, 43, 17, 'Medellín', 'https://i.pravatar.cc/300?u=pits-26', false, 4.6, 19),
  (27, 'Hugo Zapata', '3001111137', NULL, 31, 7, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-27', true, NULL, 0),
  (28, 'Mario Duarte', '3001111138', NULL, 29, 6, 'Cali', 'https://i.pravatar.cc/300?u=pits-28', true, 3.9, 4),
  (29, 'Gustavo Cardona', '3001111139', NULL, 40, 14, 'Medellín', 'https://i.pravatar.cc/300?u=pits-29', true, 4.4, 14),
  (30, 'Pablo Herrera', '3001111140', NULL, 34, 8, 'Bogotá', 'https://i.pravatar.cc/300?u=pits-30', true, 4.2, 7),
  (31, 'Nicolás Pineda', '3001111141', NULL, 37, 11, 'Cali', 'https://i.pravatar.cc/300?u=pits-31', false, 4.1, 10),
  (32, 'Alberto Quintero', '3001111142', NULL, 45, 19, 'Medellín', 'https://i.pravatar.cc/300?u=pits-32', true, 4.8, 21)
ON CONFLICT (id_mecanico) DO NOTHING;

-- Actualizar fotos en mecánicos que ya existían sin foto
UPDATE mecanico SET foto = 'https://i.pravatar.cc/300?u=pits-' || id_mecanico WHERE foto IS NULL;

SELECT setval(pg_get_serial_sequence('mecanico', 'id_mecanico'), (SELECT COALESCE(MAX(id_mecanico), 1) FROM mecanico));

-- 3. Enlaces mecánico–especialidad (1 especialidad por mecánico en este seed)
INSERT INTO mecanico_especialidad (id_mecanico, id_especialidad) VALUES
  (1, 1),  (2, 2),  (3, 3),  (4, 4),  (5, 1),  (6, 5),  (7, 6),  (8, 2),  (9, 7),  (10, 1),
  (11, 4), (12, 5), (13, 3), (14, 2), (15, 1), (16, 7), (17, 6), (18, 4), (19, 5), (20, 1),
  (21, 2), (22, 3), (23, 1), (24, 7), (25, 4), (26, 5), (27, 2), (28, 6), (29, 1), (30, 3),
  (31, 7), (32, 1)
ON CONFLICT DO NOTHING;
