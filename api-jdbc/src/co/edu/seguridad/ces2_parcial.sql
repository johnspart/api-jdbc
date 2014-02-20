drop table tbl_permisos ;
drop table tbl_paginas ;
drop table tbl_usuarios ;


create table tbl_usuarios (
    id_usuario varchar (30), --Equivalente al login del usuario
    nombre varchar (255) not null,
    apellido varchar (255) not null,
    password varchar (40),
    constraint pk_usuarios primary key (id_usuario)
);
--Password 1234, el hash SHA1 es 7110eda4d09e062aa5e4a390b0a572ac0d2c0220
INSERT INTO tbl_usuarios (id_usuario, nombre, apellido, password) VALUES ('dthomas', 'Diego', 'Thomas', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220');

create table tbl_paginas (
    id_pagina serial, 
    nombre varchar (255) not null,
    url varchar (255) not null,
    constraint pk_paginas primary key (id_pagina)
);

create table tbl_permisos (
    id_usuario  varchar (30), 
    id_pagina integer,
    constraint pk_permisos primary key (id_usuario, id_pagina),
    constraint fk_permisos_usuarios foreign key (id_usuario) references tbl_usuarios on delete cascade on update cascade,
    constraint fk_permisos_paginas foreign key (id_pagina) references tbl_paginas on delete restrict on update cascade
);