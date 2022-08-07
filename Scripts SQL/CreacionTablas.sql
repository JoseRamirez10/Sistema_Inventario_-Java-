create table USUARIO_CONTRASEÑA(
	contrasenia varchar(32) not null
);


create table cliente(
	id_cliente int,
	nombre varchar(15) not null,
	ap_paterno varchar(10) not null,
	ap_materno varchar(10) null,
	fecha_nacimiento date not null,
	calle varchar(20) not null,
	colonia varchar(15) not null,
	numero varchar(5),
	PRIMARY KEY (id_cliente)
);


create table cliente_email(
	email varchar(25),
	id_cliente int,
	PRIMARY KEY (email),
	FOREIGN KEY (id_cliente) references cliente(id_cliente)
);

create table pais(
	id_pais int,
	pais varchar(10),
	PRIMARY KEY (id_pais)
);

create table pais_cliente(
	id_pais int,
	id_cliente int,
	PRIMARY KEY (id_pais, id_cliente),
	FOREIGN KEY (id_pais) REFERENCES pais(id_pais),
	FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

create table producto(
	id_producto int,
	nombre varchar(15) not null,
	precio decimal(6,2) not null,
	cantidad int not null,
	PRIMARY KEY (id_producto)
);

create table categoria(
	id_categoria int,
	categoria varchar(25),
	PRIMARY KEY (id_categoria)
);

drop table categoria;

create table categoria_producto(
	id_categoria int,
	id_producto int,
	PRIMARY KEY (id_categoria, id_producto),
	FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
	FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

drop table categoria_producto;

create table factura(
	id_cliente int,
	id_producto int,
	cantidad_producto int not null,
	total decimal(10,2) not null,
	PRIMARY KEY (id_cliente, id_producto),
	FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

