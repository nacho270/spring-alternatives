CREATE TABLE t_user (
  id int NOT NULL,
  email text NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE t_shipment (
  id varchar(36) NOT NULL,
  total decimal(9,2) NOT NULL,
  f_user_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (f_user_id) REFERENCES t_user (id)
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
  FOREIGN KEY (f_product_id) REFERENCES t_product (id),
  FOREIGN KEY (f_shipment_id) REFERENCES t_shipment (id) ON DELETE CASCADE
);
