-- 50 direcciones
INSERT INTO address (street, number, city, postalCode)
SELECT
    (ARRAY['Calle Macedonio Alcalá','Av. Juárez','Calle Tinoco y Palacios',
            'Av. Ferrocarril','Blvd. Aeropuerto','Calle García Vigil',
            'Calle Cinco de Mayo','Av. Universidad','Calle Morelos',
            'Calle Aldama'])[((gs - 1) % 10) + 1],
    (((gs * 7) % 200) + 1)::VARCHAR,
    (ARRAY['Oaxaca','San Bartolo Coyotepec','Etla','Tlacolula',
            'Miahuatlán','Huajuapan','Tehuantepec','Juchitán',
            'Tuxtepec','Salina Cruz'])[((gs - 1) % 10) + 1],
    (67700 + gs)::VARCHAR
FROM generate_series(1, 50) AS gs;

-- 50 usuarios (password = 'password' hasheado con BCrypt)
INSERT INTO users (firstname, lastname, email, phonenumber, role, status, idaddress, profilepicture, password)
SELECT
    (ARRAY['Carlos','María','José','Ana','Luis','Laura','Miguel','Elena',
            'Jorge','Sofía','Pedro','Valentina','Andrés','Isabella','Ricardo',
            'Camila','Fernando','Gabriela','Roberto','Natalia'])[((gs - 1) % 20) + 1],
    (ARRAY['García','Martínez','López','Sánchez','Rodríguez','Pérez',
            'González','Hernández','Díaz','Morales','Jiménez','Torres',
            'Ramírez','Flores','Mendoza','Ruiz','Reyes','Cruz','Ortega','Vega'])
        [((gs - 1) % 20) + 1],
    'usuario' || gs || '@ejemplo.com',
    '+52 951 ' || (100 + gs)::VARCHAR || ' ' || (1000 + gs * 3)::VARCHAR,
    CASE WHEN gs % 10 = 0 THEN 'Admin' ELSE 'User' END,
    CASE WHEN gs % 7  = 0 THEN 'Inactive' ELSE 'Active' END,
    gs,
    'https://i.pravatar.cc/150?img=' || gs,
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' -- password: "password"
FROM generate_series(1, 50) AS gs;

-- Usuario admin por defecto
INSERT INTO users (firstname, lastname, email, phonenumber, role, status, password)
VALUES ('Admin', 'Default', 'admin@example.com', '+52 951 000 0000', 'Admin', 'Active',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi');