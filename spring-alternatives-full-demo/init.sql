drop schema if exists alternatives_demo;
create schema if not exists alternatives_demo;

use alternatives_demo;

CREATE TABLE t_user (
  id int NOT NULL,
  email text NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE t_shipment (
  id varchar(36) NOT NULL,
  total decimal(9,2) NOT NULL,
  f_user_id int NOT NULL,
  KEY f_user_id (f_user_id),
  PRIMARY KEY (id),
  CONSTRAINT shipment_user_pk FOREIGN KEY (f_user_id) REFERENCES t_user (id)
);


CREATE TABLE t_product (
  id varchar(36) NOT NULL,
  name text NOT NULL,
  price decimal(9,2) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE t_item (
  id varchar(36) NOT NULL,
  quantity int NOT NULL,
  f_product_id varchar(36) NOT NULL,
  f_shipment_id varchar(36) NOT NULL,
  PRIMARY KEY (id),
  KEY f_product_id (f_product_id),
  KEY f_shipment_id (f_shipment_id),
  CONSTRAINT item_ibfk_1 FOREIGN KEY (f_product_id) REFERENCES t_product (id),
  CONSTRAINT item_ibfk_2 FOREIGN KEY (f_shipment_id) REFERENCES t_shipment (id) ON DELETE CASCADE
);
