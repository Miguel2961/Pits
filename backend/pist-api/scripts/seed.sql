-- Seed data: especialidades y mecánicos (mock para MVP - vista cliente).
-- Ejecutar UNA vez sobre la base `pits` después de que las tablas existan (por ejemplo tras el primer arranque de la app).
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

-- 2. Mecánicos (32 registros desde mock)
INSERT INTO mecanico (id_mecanico, nombre, contacto, certificacion, edad, experiencia, ciudad, disponible) VALUES
  (1, 'Carlos Gómez', '3001111111', NULL, 35, 10, 'Medellín', true),
  (2, 'Laura Díaz', '3001111112', NULL, 29, 6, 'Bogotá', true),
  (3, 'Andrés López', '3001111113', NULL, 41, 15, 'Cali', false),
  (4, 'Juan Pérez', '3001111114', NULL, 38, 12, 'Medellín', true),
  (5, 'Miguel Torres', '3001111115', NULL, 33, 8, 'Barranquilla', true),
  (6, 'Daniel Rojas', '3001111116', NULL, 45, 20, 'Bogotá', false),
  (7, 'Felipe Vargas', '3001111117', NULL, 31, 7, 'Cali', true),
  (8, 'Luis Ramírez', '3001111118', NULL, 40, 14, 'Medellín', true),
  (9, 'Sebastián Castro', '3001111119', NULL, 27, 5, 'Bogotá', true),
  (10, 'Oscar Martínez', '3001111120', NULL, 36, 11, 'Cali', false),
  (11, 'David Hernández', '3001111121', NULL, 34, 9, 'Medellín', true),
  (12, 'Ricardo Gómez', '3001111122', NULL, 48, 22, 'Bogotá', false),
  (13, 'Camilo Restrepo', '3001111123', NULL, 30, 6, 'Medellín', true),
  (14, 'Jorge Sánchez', '3001111124', NULL, 42, 16, 'Barranquilla', true),
  (15, 'Diego Molina', '3001111125', NULL, 39, 13, 'Bogotá', true),
  (16, 'Pedro Salazar', '3001111126', NULL, 37, 12, 'Cali', false),
  (17, 'Esteban Cárdenas', '3001111127', NULL, 32, 7, 'Medellín', true),
  (18, 'Santiago Ruiz', '3001111128', NULL, 28, 5, 'Bogotá', true),
  (19, 'Alejandro Moreno', '3001111129', NULL, 44, 18, 'Cali', false),
  (20, 'Cristian Delgado', '3001111130', NULL, 33, 9, 'Medellín', true),
  (21, 'Mauricio Giraldo', '3001111131', NULL, 41, 15, 'Bogotá', true),
  (22, 'Iván Ospina', '3001111132', NULL, 36, 10, 'Cali', false),
  (23, 'Henry López', '3001111133', NULL, 47, 21, 'Medellín', true),
  (24, 'Fernando Arias', '3001111134', NULL, 38, 13, 'Bogotá', true),
  (25, 'Wilson Mejía', '3001111135', NULL, 35, 10, 'Cali', true),
  (26, 'Raúl Patiño', '3001111136', NULL, 43, 17, 'Medellín', false),
  (27, 'Hugo Zapata', '3001111137', NULL, 31, 7, 'Bogotá', true),
  (28, 'Mario Duarte', '3001111138', NULL, 29, 6, 'Cali', true),
  (29, 'Gustavo Cardona', '3001111139', NULL, 40, 14, 'Medellín', true),
  (30, 'Pablo Herrera', '3001111140', NULL, 34, 8, 'Bogotá', true),
  (31, 'Nicolás Pineda', '3001111141', NULL, 37, 11, 'Cali', false),
  (32, 'Alberto Quintero', '3001111142', NULL, 45, 19, 'Medellín', true)
ON CONFLICT (id_mecanico) DO NOTHING;

SELECT setval(pg_get_serial_sequence('mecanico', 'id_mecanico'), (SELECT COALESCE(MAX(id_mecanico), 1) FROM mecanico));

-- 3. Enlaces mecánico–especialidad (1 especialidad por mecánico en este seed)
INSERT INTO mecanico_especialidad (id_mecanico, id_especialidad) VALUES
  (1, 1),  (2, 2),  (3, 3),  (4, 4),  (5, 1),  (6, 5),  (7, 6),  (8, 2),  (9, 7),  (10, 1),
  (11, 4), (12, 5), (13, 3), (14, 2), (15, 1), (16, 7), (17, 6), (18, 4), (19, 5), (20, 1),
  (21, 2), (22, 3), (23, 1), (24, 7), (25, 4), (26, 5), (27, 2), (28, 6), (29, 1), (30, 3),
  (31, 7), (32, 1)
ON CONFLICT DO NOTHING;
