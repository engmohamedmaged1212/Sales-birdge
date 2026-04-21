ALTER TABLE product_variants
    RENAME COLUMN price_override TO price;

ALTER TABLE product_variants
    MODIFY COLUMN price DECIMAL(10,2) NOT NULL;