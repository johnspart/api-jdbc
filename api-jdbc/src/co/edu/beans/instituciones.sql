-- Table: instituciones

-- DROP TABLE instituciones;

CREATE TABLE instituciones
(
  id_institucion bigserial NOT NULL,
  nombre character varying,
  direccion character varying,
  comentario character varying,
  estado boolean,
  CONSTRAINT pk_id_institucion PRIMARY KEY (id_institucion )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE instituciones
  OWNER TO postgres;
