-- CLIENTE
insert into cliente values(1,'Jose', 'Ramirez', 'Gonzalez','1997-12-15','Pariam','Magdalena', 'SN');
insert into cliente values(2,'Jesus', 'Ramirez', 'Gonzalez','1997-12-15','Pariam','Magdalena', 'SN');
insert into cliente values(3,'Mauricio', 'Sanchez',null,'2002-07-02','De los Niños','Magdalena', 'SN');
insert into cliente values(4,'Alberto', 'Estrada', null,'1992-02-24','De la Junta','Magdalena', 'SN');
insert into cliente values(5,'Miguel', 'Angeles', 'Rosas','1996-05-12','Pariam','Magdalena', 'SN');

select * from cliente;

-- CLIENTE_EMAIL
insert into cliente_email values('jozeta_fama@hotmail.com', 1);
insert into cliente_email values('namida26@outlook.com', 2);
insert into cliente_email values('mauricioSanch@email.com', 3);
insert into cliente_email values('estrada_a@email.com', 4);
insert into cliente_email values('jose_snake@hotmail.com', 5);

select * from cliente_email;

-- PAIS
insert into pais values(1, 'Mexico');
insert into pais values(2, 'EUA');
insert into pais values(3, 'Rusia');

select * from pais;

-- PAIS_CLIENTE
insert into pais_cliente values(1, 1);
insert into pais_cliente values(1, 2);
insert into pais_cliente values(1, 3);
insert into pais_cliente values(1, 4);
insert into pais_cliente values(1, 5);

select * from pais_cliente;

select cliente.id_cliente, cliente.nombre, cliente.ap_paterno, cliente.ap_materno, cliente.fecha_nacimiento,
	cliente.calle, cliente.colonia, cliente.numero,
	cliente_email.email, pais.pais 
	from cliente inner join cliente_email on cliente.id_cliente = cliente_email.id_cliente
	inner join pais_cliente on cliente.id_cliente = pais_cliente.id_cliente
	inner join pais on pais_cliente.id_pais = pais.id_pais;

-- PRODUCTO
insert into producto values(1, 'Television 24"', 4500.00, 25);
insert into producto values(2, 'PlayStation 4', 7000.00, 12);
insert into producto values(3, 'Xbox Series S', 5700.00, 7);
insert into producto values(4, 'Macbook Pro 15"', 9000.00, 2);
insert into producto values(5, 'PC Gamer', 9000.00, 14);

insert into producto values(7, 'Prueba', 0, 0);

select * from producto;

-- CATEGORIA
insert into categoria values(1, 'Electrodomesticos');
insert into categoria values(2, 'Videojuegos');
insert into categoria values(3, 'Informatica');

select * from categoria;

-- CATEGORIA_PRODUCTO
insert into categoria_producto values(1,1);
insert into categoria_producto values(2,2);
insert into categoria_producto values(2,3);
insert into categoria_producto values(3,4);
insert into categoria_producto values(3,5);

insert into categoria_producto values(1,6);
insert into categoria_producto values(1,7);

select * from categoria_producto;

select producto.id_producto, producto.nombre, producto.precio, producto.cantidad,
	categoria.categoria 
	from producto inner join categoria_producto 
	on producto.id_producto = categoria_producto.id_producto
	inner join categoria on categoria.id_categoria = categoria_producto.id_categoria;

-- FACTURA
insert into factura values (1, 1, 2, 9000);
insert into factura values (1, 2, 1, 7000);
insert into factura values (2, 5, 1, 9000);
insert into factura values (5, 5, 1, 9000);

select * from factura;

select cliente.nombre, producto.nombre, producto.precio,
	factura.cantidad_producto, factura.total
	from factura
	inner join cliente on factura.id_cliente = cliente.id_cliente
	inner join producto on factura.id_producto = producto.id_producto; 

