SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE customers DROP FOREIGN KEY fk_customer_created_by;
ALTER TABLE customers DROP FOREIGN KEY fk_customer_updated_by;
ALTER TABLE addresses DROP FOREIGN KEY fk_addr_customer;
ALTER TABLE aktionen  DROP FOREIGN KEY fk_aktion_product;
ALTER TABLE gutscheine DROP FOREIGN KEY fk_gutschein_created_by;
ALTER TABLE orders     DROP FOREIGN KEY fk_order_customer;
ALTER TABLE orders     DROP FOREIGN KEY fk_order_agent;
ALTER TABLE orders     DROP FOREIGN KEY fk_order_rechnung;
ALTER TABLE orders     DROP FOREIGN KEY fk_order_liefer;
ALTER TABLE order_items DROP FOREIGN KEY fk_item_order;
ALTER TABLE order_items DROP FOREIGN KEY fk_item_product;
ALTER TABLE order_items DROP FOREIGN KEY fk_item_aktion;
ALTER TABLE gutschein_usage DROP FOREIGN KEY fk_usage_gutschein;
ALTER TABLE gutschein_usage DROP FOREIGN KEY fk_usage_customer;
ALTER TABLE gutschein_usage DROP FOREIGN KEY fk_usage_order;
ALTER TABLE gutschein_usage DROP FOREIGN KEY fk_usage_order_item;
ALTER TABLE gutschein_usage DROP FOREIGN KEY fk_usage_agent;
ALTER TABLE preisanpassungen DROP FOREIGN KEY fk_anpassung_order_item;
ALTER TABLE preisanpassungen DROP FOREIGN KEY fk_anpassung_customer;
ALTER TABLE preisanpassungen DROP FOREIGN KEY fk_anpassung_user;

ALTER TABLE users MODIFY user_id BIGINT AUTO_INCREMENT;

ALTER TABLE customers MODIFY customer_id BIGINT, MODIFY created_by BIGINT, MODIFY updated_by BIGINT;

ALTER TABLE addresses MODIFY address_id BIGINT AUTO_INCREMENT, MODIFY customer_id BIGINT;

ALTER TABLE products MODIFY product_id BIGINT AUTO_INCREMENT;

ALTER TABLE aktionen MODIFY aktion_id BIGINT AUTO_INCREMENT, MODIFY product_id BIGINT;

ALTER TABLE gutscheine MODIFY gutschein_id BIGINT AUTO_INCREMENT, MODIFY created_by BIGINT;

ALTER TABLE orders
    MODIFY order_id BIGINT AUTO_INCREMENT,
    MODIFY customer_id BIGINT,
    MODIFY agent_id BIGINT,
    MODIFY rechnungsadresse_id BIGINT,
    MODIFY lieferadresse_id BIGINT;

ALTER TABLE order_items
    MODIFY item_id BIGINT AUTO_INCREMENT,
    MODIFY order_id BIGINT,
    MODIFY product_id BIGINT,
    MODIFY aktion_id BIGINT;

ALTER TABLE gutschein_usage
    MODIFY usage_id BIGINT AUTO_INCREMENT,
    MODIFY gutschein_id BIGINT,
    MODIFY customer_id BIGINT,
    MODIFY order_id BIGINT,
    MODIFY order_item_id BIGINT,
    MODIFY agent_id BIGINT;

ALTER TABLE preisanpassungen
    MODIFY anpassung_id BIGINT AUTO_INCREMENT,
    MODIFY order_item_id BIGINT,
    MODIFY customer_id BIGINT,
    MODIFY geaendert_von BIGINT;

ALTER TABLE customers ADD CONSTRAINT fk_customer_created_by FOREIGN KEY (created_by) REFERENCES users(user_id);
ALTER TABLE customers ADD CONSTRAINT fk_customer_updated_by FOREIGN KEY (updated_by) REFERENCES users(user_id);
ALTER TABLE addresses ADD CONSTRAINT fk_addr_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE;
ALTER TABLE aktionen  ADD CONSTRAINT fk_aktion_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE;
ALTER TABLE gutscheine ADD CONSTRAINT fk_gutschein_created_by FOREIGN KEY (created_by) REFERENCES users(user_id);
ALTER TABLE orders     ADD CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE orders     ADD CONSTRAINT fk_order_agent FOREIGN KEY (agent_id) REFERENCES users(user_id);
ALTER TABLE orders     ADD CONSTRAINT fk_order_rechnung FOREIGN KEY (rechnungsadresse_id) REFERENCES addresses(address_id);
ALTER TABLE orders     ADD CONSTRAINT fk_order_liefer FOREIGN KEY (lieferadresse_id) REFERENCES addresses(address_id);
ALTER TABLE order_items ADD CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE;
ALTER TABLE order_items ADD CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products(product_id);
ALTER TABLE order_items ADD CONSTRAINT fk_item_aktion FOREIGN KEY (aktion_id) REFERENCES aktionen(aktion_id) ON DELETE SET NULL;
ALTER TABLE gutschein_usage ADD CONSTRAINT fk_usage_gutschein FOREIGN KEY (gutschein_id) REFERENCES gutscheine(gutschein_id);
ALTER TABLE gutschein_usage ADD CONSTRAINT fk_usage_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE gutschein_usage ADD CONSTRAINT fk_usage_order FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE gutschein_usage ADD CONSTRAINT fk_usage_order_item FOREIGN KEY (order_item_id) REFERENCES order_items(item_id);
ALTER TABLE gutschein_usage ADD CONSTRAINT fk_usage_agent FOREIGN KEY (agent_id) REFERENCES users(user_id);
ALTER TABLE preisanpassungen ADD CONSTRAINT fk_anpassung_order_item FOREIGN KEY (order_item_id) REFERENCES order_items(item_id);
ALTER TABLE preisanpassungen ADD CONSTRAINT fk_anpassung_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE preisanpassungen ADD CONSTRAINT fk_anpassung_user FOREIGN KEY (geaendert_von) REFERENCES users(user_id);

SET FOREIGN_KEY_CHECKS = 1;