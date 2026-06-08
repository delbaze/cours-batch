CREATE TABLE IF NOT EXISTS produit (
   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
   reference   VARCHAR(50)  NOT NULL UNIQUE,
    designation VARCHAR(255) NOT NULL,
    prix        DOUBLE       NOT NULL,
    stock       INT          NOT NULL,
    date_import DATE
    );