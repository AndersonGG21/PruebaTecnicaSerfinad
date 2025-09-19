-- A) Obtener todos los pedidos con información del usuario y producto
SELECT 
    p.id AS pedido_id,
    p.cantidad,
    p.fecha,
    u.id AS usuario_id,
    u.nombre AS usuario_nombre,
    u.email AS usuario_email,
    pr.id AS producto_id,
    pr.nombre AS producto_nombre,
    pr.precio AS producto_precio,
    (p.cantidad * pr.precio) AS total_pedido
FROM pedidos p
INNER JOIN usuarios u ON p.usuario_id = u.id
INNER JOIN productos pr ON p.producto_id = pr.id
ORDER BY p.fecha DESC, p.id;


-- B) Calcular el total gastado por cada usuario
SELECT 
    u.id AS usuario_id,
    u.nombre AS usuario_nombre,
    u.email,
    COUNT(p.id) AS total_pedidos,
    SUM(p.cantidad) AS productos_comprados,
    SUM(p.cantidad * pr.precio) AS total_gastado
FROM usuarios u
INNER JOIN pedidos p ON u.id = p.usuario_id
INNER JOIN productos pr ON p.producto_id = pr.id
GROUP BY u.id, u.nombre, u.email
ORDER BY SUM(p.cantidad * pr.precio) DESC;

-- C) Encontrar productos sin pedidos - productos no vendidos
SELECT 
    pr.id AS producto_id,
    pr.nombre AS producto_nombre,
    pr.precio,
    pr.stock
FROM productos pr
LEFT JOIN pedidos p ON pr.id = p.producto_id
WHERE p.producto_id IS NULL
ORDER BY pr.nombre;


-- D) Obtener el producto más vendido por cantidad total
SELECT 
    pr.id AS producto_id,
    pr.nombre AS producto_nombre,
    pr.precio,
    SUM(p.cantidad) AS cantidad_total_vendida,
    COUNT(p.id) AS numero_pedidos,
    SUM(p.cantidad * pr.precio) AS ingresos_totales
FROM productos pr
INNER JOIN pedidos p ON pr.id = p.producto_id
GROUP BY pr.id, pr.nombre, pr.precio
ORDER BY SUM(p.cantidad) DESC
LIMIT 1;
