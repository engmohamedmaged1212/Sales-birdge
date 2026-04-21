CREATE TABLE users (
                       user_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username    VARCHAR(50)  NOT NULL UNIQUE,
                       password    VARCHAR(255) NOT NULL,
                       role        ENUM('AGENT','TEAMLEADER','ADMIN') NOT NULL DEFAULT 'AGENT',
                       is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
                       created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customers (
                           customer_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
                           first_name   VARCHAR(50)  NOT NULL,
                           last_name    VARCHAR(50)  NOT NULL,
                           phone_number VARCHAR(20),
                           date_of_birth DATE,
                           gender       ENUM('MALE','FEMALE'),
                           status       ENUM('active','blocked','employee') NOT NULL DEFAULT 'active',
                           credit_limit DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                           created_by   BIGINT       NOT NULL,
                           created_at   DATETIME     DEFAULT CURRENT_TIMESTAMP,
                           updated_by   BIGINT       DEFAULT NULL,
                           updated_at   DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

                           CONSTRAINT fk_customer_created_by FOREIGN KEY (created_by) REFERENCES users(user_id),
                           CONSTRAINT fk_customer_updated_by FOREIGN KEY (updated_by) REFERENCES users(user_id)
);

CREATE TABLE addresses (
                           address_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                           customer_id        BIGINT       NOT NULL,
                           address_type       ENUM('billing','shipping') NOT NULL,

                           first_name         VARCHAR(50),
                           last_name          VARCHAR(50),
                           company_name       VARCHAR(100),

                           is_parcel_shop     BOOLEAN      NOT NULL DEFAULT FALSE,
                           parcel_shop_provider ENUM('DHL','DPD','Hermes','GLS','UPS') DEFAULT NULL,
                           parcel_shop_id     VARCHAR(20)  DEFAULT NULL,

                           street             VARCHAR(100) NOT NULL,
                           house_number       VARCHAR(10)  NOT NULL,
                           address_supplement VARCHAR(100),

                           plz                VARCHAR(10)  NOT NULL,
                           city               VARCHAR(50)  NOT NULL,
                           state              VARCHAR(50),
                           country            VARCHAR(50)  NOT NULL DEFAULT 'Germany',
                           country_code       CHAR(2)      NOT NULL DEFAULT 'DE',

                           is_default         BOOLEAN      NOT NULL DEFAULT FALSE,
                           created_at         DATETIME     DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_addr_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
                           CONSTRAINT chk_parcel_shop CHECK (
                               (is_parcel_shop = FALSE AND parcel_shop_provider IS NULL     AND parcel_shop_id IS NULL) OR
                               (is_parcel_shop = TRUE  AND parcel_shop_provider IS NOT NULL AND parcel_shop_id IS NOT NULL)
                               )
);

CREATE TABLE products (
                          product_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                          product_name VARCHAR(150)  NOT NULL,
                          description  TEXT,
                          size         ENUM('normal','xxl','oepdl') NOT NULL DEFAULT 'normal',
                          is_electrical BOOLEAN      NOT NULL DEFAULT FALSE,
                          availability ENUM('available','waitlist','sold_out') NOT NULL DEFAULT 'available',
                          stock        INT           NOT NULL DEFAULT 0,
                          base_price   DECIMAL(10,2) NOT NULL,
                          created_at   DATETIME      DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_variants (
                                  variant_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  product_id    BIGINT        NOT NULL,
                                  variant_label VARCHAR(200)  NOT NULL,
                                  sku           VARCHAR(100)  NOT NULL UNIQUE,
                                  price_override DECIMAL(10,2) DEFAULT NULL,
                                  stock         INT           NOT NULL DEFAULT 0,
                                  is_active     BOOLEAN       NOT NULL DEFAULT TRUE,
                                  created_at    DATETIME      DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

CREATE TABLE variant_attribute_definitions (
                                               definition_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               product_id    BIGINT      NOT NULL,
                                               attribute_key VARCHAR(50) NOT NULL,
                                               display_name  VARCHAR(100) NOT NULL,
                                               sort_order    INT         NOT NULL DEFAULT 0,

                                               CONSTRAINT fk_attr_def_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
                                               CONSTRAINT uq_attr_def UNIQUE (product_id, attribute_key)
);

CREATE TABLE variant_attributes (
                                    attribute_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    variant_id      BIGINT       NOT NULL,
                                    definition_id   BIGINT       NOT NULL,
                                    attribute_value VARCHAR(100) NOT NULL,

                                    CONSTRAINT fk_var_attr_variant     FOREIGN KEY (variant_id)    REFERENCES product_variants(variant_id) ON DELETE CASCADE,
                                    CONSTRAINT fk_var_attr_definition  FOREIGN KEY (definition_id) REFERENCES variant_attribute_definitions(definition_id) ON DELETE CASCADE,
                                    CONSTRAINT uq_variant_attribute    UNIQUE (variant_id, definition_id)
);

CREATE TABLE promotions (
                            promotion_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                            product_id       BIGINT        NOT NULL,
                            discount_percent DECIMAL(5,2)  DEFAULT NULL,
                            discount_amount  DECIMAL(10,2) DEFAULT NULL,
                            promotion_price  DECIMAL(10,2) DEFAULT NULL,
                            valid_from       DATE          NOT NULL,
                            valid_until      DATE          NOT NULL,
                            is_active        BOOLEAN       NOT NULL DEFAULT TRUE,

                            CONSTRAINT fk_promotion_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
                            CONSTRAINT chk_promotion_dates CHECK (valid_from <= valid_until),
                            CONSTRAINT chk_promotion_discount CHECK (
                                (discount_percent IS NOT NULL AND discount_amount IS NULL  AND promotion_price IS NULL) OR
                                (discount_amount  IS NOT NULL AND discount_percent IS NULL AND promotion_price IS NULL) OR
                                (promotion_price  IS NOT NULL AND discount_percent IS NULL AND discount_amount IS NULL)
                                )
);

CREATE TABLE coupons (
                         coupon_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
                         code            VARCHAR(50)   NOT NULL UNIQUE,
                         discount_amount DECIMAL(10,2) NOT NULL,
                         created_by      BIGINT        NOT NULL,
                         created_at      DATETIME      DEFAULT CURRENT_TIMESTAMP,
                         valid_from      DATE          NOT NULL,
                         valid_until     DATE          NOT NULL,
                         is_active       BOOLEAN       NOT NULL DEFAULT TRUE,

                         CONSTRAINT fk_coupon_created_by FOREIGN KEY (created_by) REFERENCES users(user_id),
                         CONSTRAINT chk_coupon_dates CHECK (valid_from <= valid_until)
);

CREATE TABLE orders (
                        order_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id        BIGINT        NOT NULL,
                        agent_id           BIGINT        NOT NULL,
                        billing_address_id  BIGINT        NOT NULL,
                        shipping_address_id BIGINT        NOT NULL,
                        payment_method     ENUM('invoice','cash_on_delivery','credit_card','direct_debit') NOT NULL,
                        status             ENUM('new','processing','shipped','cancelled') NOT NULL DEFAULT 'new',

                        total_gross        DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        discount_amount    DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        final_amount       DECIMAL(10,2) NOT NULL DEFAULT 0.00,

                        notes              TEXT,
                        order_date         DATETIME      DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id)         REFERENCES customers(customer_id),
                        CONSTRAINT fk_order_agent    FOREIGN KEY (agent_id)             REFERENCES users(user_id),
                        CONSTRAINT fk_order_billing  FOREIGN KEY (billing_address_id)  REFERENCES addresses(address_id),
                        CONSTRAINT fk_order_shipping FOREIGN KEY (shipping_address_id) REFERENCES addresses(address_id)
);

CREATE TABLE order_items (
                             item_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id     BIGINT        NOT NULL,
                             product_id   BIGINT        NOT NULL,
                             variant_id   BIGINT        DEFAULT NULL,
                             quantity     INT           NOT NULL DEFAULT 1,
                             unit_price   DECIMAL(10,2) NOT NULL,
                             promotion_id BIGINT        DEFAULT NULL,

                             CONSTRAINT fk_item_order    FOREIGN KEY (order_id)     REFERENCES orders(order_id) ON DELETE CASCADE,
                             CONSTRAINT fk_item_product  FOREIGN KEY (product_id)   REFERENCES products(product_id),
                             CONSTRAINT fk_item_variant  FOREIGN KEY (variant_id)   REFERENCES product_variants(variant_id) ON DELETE SET NULL,
                             CONSTRAINT fk_item_promotion FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id) ON DELETE SET NULL,
                             CONSTRAINT chk_quantity CHECK (quantity > 0)
);

CREATE TABLE coupon_usage (
                              usage_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
                              coupon_id       BIGINT        NOT NULL,
                              customer_id     BIGINT        NOT NULL,
                              order_id        BIGINT        NOT NULL,
                              order_item_id   BIGINT        NOT NULL,
                              agent_id        BIGINT        NOT NULL,
                              discount_amount DECIMAL(10,2) NOT NULL,
                              used_at         DATETIME      DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT uq_coupon_customer   UNIQUE (coupon_id, customer_id),
                              CONSTRAINT fk_coupon_usage_coupon     FOREIGN KEY (coupon_id)     REFERENCES coupons(coupon_id),
                              CONSTRAINT fk_coupon_usage_customer   FOREIGN KEY (customer_id)   REFERENCES customers(customer_id),
                              CONSTRAINT fk_coupon_usage_order      FOREIGN KEY (order_id)      REFERENCES orders(order_id),
                              CONSTRAINT fk_coupon_usage_order_item FOREIGN KEY (order_item_id) REFERENCES order_items(item_id),
                              CONSTRAINT fk_coupon_usage_agent      FOREIGN KEY (agent_id)      REFERENCES users(user_id)
);

CREATE TABLE price_adjustments (
                                   adjustment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   order_item_id BIGINT        NOT NULL,
                                   customer_id   BIGINT        NOT NULL,
                                   old_price     DECIMAL(10,2) NOT NULL,
                                   new_price     DECIMAL(10,2) NOT NULL,
                                   changed_by    BIGINT        NOT NULL,
                                   description   TEXT          NOT NULL,
                                   changed_at    DATETIME      DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT fk_price_adj_order_item FOREIGN KEY (order_item_id) REFERENCES order_items(item_id),
                                   CONSTRAINT fk_price_adj_customer   FOREIGN KEY (customer_id)   REFERENCES customers(customer_id),
                                   CONSTRAINT fk_price_adj_user       FOREIGN KEY (changed_by)    REFERENCES users(user_id),
                                   CONSTRAINT chk_new_price_lower     CHECK (new_price < old_price)
);