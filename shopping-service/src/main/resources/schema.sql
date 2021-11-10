DROP TABLE IF EXISTS tlb_invoices;

CREATE TABLE tlb_invoices (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  number_invoice VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  customer_id BIGINT,
  create_at TIMESTAMP,
  status VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS tbl_invoce_items;

CREATE TABLE tbl_invoce_items (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  invoice_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity NUMERIC,
  price DOUBLE
);

