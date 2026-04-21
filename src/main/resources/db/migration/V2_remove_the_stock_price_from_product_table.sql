-- V2: Remove stock from products table.
-- Stock is tracked per-variant on product_variants.
-- For simple products (no variants), stock will be managed
-- via a single variant or handled at the application level.

ALTER TABLE products DROP COLUMN stock;