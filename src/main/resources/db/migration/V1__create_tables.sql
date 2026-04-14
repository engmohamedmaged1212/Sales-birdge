-- ================================================
-- 1. USERS (Agents & Admins & Team Leader)
-- ================================================
CREATE TABLE users (
                       user_id     INT AUTO_INCREMENT PRIMARY KEY,
                       username    VARCHAR(50)  NOT NULL UNIQUE,
                       password    VARCHAR(255) NOT NULL,
                       role        ENUM('AGENT', 'TEAMLEADER', 'ADMIN') NOT NULL DEFAULT 'AGENT',
                       is_active   BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- 2. CUSTOMERS
-- ================================================
CREATE TABLE customers (
                           customer_id     INT PRIMARY KEY,
                           vorname         VARCHAR(50)  NOT NULL,
                           nachname        VARCHAR(50)  NOT NULL,
                           telefonnummer   VARCHAR(20),
                           status          ENUM('aktiv', 'blockiert', 'mitarbeiter') NOT NULL DEFAULT 'aktiv',
                           kreditlimit     DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                           created_by      INT          NOT NULL,
                           created_at      DATETIME     DEFAULT CURRENT_TIMESTAMP,
                           updated_by      INT          DEFAULT NULL,
                           updated_at      DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

                           CONSTRAINT fk_customer_created_by
                               FOREIGN KEY (created_by) REFERENCES users(user_id),
                           CONSTRAINT fk_customer_updated_by
                               FOREIGN KEY (updated_by) REFERENCES users(user_id)
);

-- ================================================
-- 3. ADDRESSES
-- ================================================
CREATE TABLE addresses (
                           address_id          INT AUTO_INCREMENT PRIMARY KEY,
                           customer_id         INT NOT NULL,
                           address_type        ENUM('Rechnungsadresse', 'Lieferadresse') NOT NULL,

    -- Name
                           vorname             VARCHAR(50)  NOT NULL,
                           nachname            VARCHAR(50)  NOT NULL,
                           firma               VARCHAR(100),

    -- Paketshop / Packstation
                           ist_paketshop       BOOLEAN      NOT NULL DEFAULT FALSE,
                           paketshop_anbieter  ENUM('DHL', 'DPD', 'Hermes', 'GLS', 'UPS') DEFAULT NULL,
                           paketshop_id        VARCHAR(20)  DEFAULT NULL,

    -- Street
                           strasse             VARCHAR(100) NOT NULL,
                           hausnummer          VARCHAR(10)  NOT NULL,
                           adresszusatz        VARCHAR(100),

    -- Location
                           plz                 VARCHAR(10)  NOT NULL,
                           ort                 VARCHAR(50)  NOT NULL,
                           bundesland          VARCHAR(50),
                           land                VARCHAR(50)  NOT NULL DEFAULT 'Deutschland',
                           laendercode         CHAR(2)      NOT NULL DEFAULT 'DE',

                           is_default          BOOLEAN      NOT NULL DEFAULT FALSE,
                           created_at          DATETIME     DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_addr_customer
                               FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
                                   ON DELETE CASCADE,

                           CONSTRAINT chk_paketshop
                               CHECK (
                                   (ist_paketshop = FALSE AND paketshop_anbieter IS NULL AND paketshop_id IS NULL) OR
                                   (ist_paketshop = TRUE  AND paketshop_anbieter IS NOT NULL AND paketshop_id IS NOT NULL)
                                   )
);

-- ================================================
-- 4. PRODUCTS
-- ================================================
CREATE TABLE products (
                          product_id      INT AUTO_INCREMENT PRIMARY KEY,
                          produktname     VARCHAR(150)  NOT NULL,
                          beschreibung    TEXT,
                          groesse         ENUM('Normal', 'XXL', 'Oepdl') NOT NULL DEFAULT 'Normal',
                          ist_elektrog    BOOLEAN       NOT NULL DEFAULT FALSE,
                          verfuegbarkeit  ENUM('verfuegbar', 'warteliste', 'ausverkauft') NOT NULL DEFAULT 'verfuegbar',
                          lagerbestand    INT           NOT NULL DEFAULT 0,
                          normalpreis     DECIMAL(10,2) NOT NULL,
                          created_at      DATETIME      DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- 5. AKTIONEN (عروض بتاريخ بداية ونهاية)
-- ================================================
CREATE TABLE aktionen (
                          aktion_id       INT AUTO_INCREMENT PRIMARY KEY,
                          product_id      INT           NOT NULL,
                          rabatt_prozent  DECIMAL(5,2)  DEFAULT NULL,
                          rabatt_betrag   DECIMAL(10,2) DEFAULT NULL,
                          aktionspreis    DECIMAL(10,2) DEFAULT NULL,
                          gueltig_von     DATE          NOT NULL,
                          gueltig_bis     DATE          NOT NULL,
                          ist_aktiv       BOOLEAN       NOT NULL DEFAULT TRUE,

                          CONSTRAINT fk_aktion_product
                              FOREIGN KEY (product_id) REFERENCES products(product_id)
                                  ON DELETE CASCADE,

                          CONSTRAINT chk_aktion_dates
                              CHECK (gueltig_von <= gueltig_bis),

                          CONSTRAINT chk_aktion_rabatt
                              CHECK (
                                  (rabatt_prozent IS NOT NULL AND rabatt_betrag IS NULL     AND aktionspreis IS NULL) OR
                                  (rabatt_betrag  IS NOT NULL AND rabatt_prozent IS NULL    AND aktionspreis IS NULL) OR
                                  (aktionspreis   IS NOT NULL AND rabatt_prozent IS NULL    AND rabatt_betrag IS NULL)
                                  )
);

-- ================================================
-- 6. GUTSCHEINE
-- ================================================
CREATE TABLE gutscheine (
                            gutschein_id    INT AUTO_INCREMENT PRIMARY KEY,
                            code            VARCHAR(50)   NOT NULL UNIQUE,
                            rabatt_betrag   DECIMAL(10,2) NOT NULL,
                            created_by      INT           NOT NULL,
                            created_at      DATETIME      DEFAULT CURRENT_TIMESTAMP,
                            gueltig_von     DATE          NOT NULL,
                            gueltig_bis     DATE          NOT NULL,
                            ist_aktiv       BOOLEAN       NOT NULL DEFAULT TRUE,

                            CONSTRAINT fk_gutschein_created_by
                                FOREIGN KEY (created_by) REFERENCES users(user_id),

                            CONSTRAINT chk_gutschein_dates
                                CHECK (gueltig_von <= gueltig_bis)
);

-- ================================================
-- 7. ORDERS
-- ================================================
CREATE TABLE orders (
                        order_id                INT AUTO_INCREMENT PRIMARY KEY,
                        customer_id             INT           NOT NULL,
                        agent_id                INT           NOT NULL,
                        rechnungsadresse_id     INT           NOT NULL,
                        lieferadresse_id        INT           NOT NULL,
                        zahlungsart             ENUM('Rechnung', 'Nachnahme', 'Kreditkarte', 'Bankeinzug') NOT NULL,
                        status                  ENUM('neu', 'bearbeitung', 'versendet', 'storniert') NOT NULL DEFAULT 'neu',

                        gesamtbetrag_brutto     DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        rabatt_betrag           DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        endbetrag               DECIMAL(10,2) NOT NULL DEFAULT 0.00,

                        notizen                 TEXT,
                        order_date              DATETIME      DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_order_customer
                            FOREIGN KEY (customer_id)         REFERENCES customers(customer_id),
                        CONSTRAINT fk_order_agent
                            FOREIGN KEY (agent_id)            REFERENCES users(user_id),
                        CONSTRAINT fk_order_rechnung
                            FOREIGN KEY (rechnungsadresse_id) REFERENCES addresses(address_id),
                        CONSTRAINT fk_order_liefer
                            FOREIGN KEY (lieferadresse_id)    REFERENCES addresses(address_id)
);

-- ================================================
-- 8. ORDER ITEMS
-- ================================================
CREATE TABLE order_items (
                             item_id         INT AUTO_INCREMENT PRIMARY KEY,
                             order_id        INT           NOT NULL,
                             product_id      INT           NOT NULL,
                             menge           INT           NOT NULL DEFAULT 1,
                             einzelpreis     DECIMAL(10,2) NOT NULL,
                             aktion_id       INT           DEFAULT NULL,

                             CONSTRAINT fk_item_order
                                 FOREIGN KEY (order_id)   REFERENCES orders(order_id)
                                     ON DELETE CASCADE,
                             CONSTRAINT fk_item_product
                                 FOREIGN KEY (product_id) REFERENCES products(product_id),
                             CONSTRAINT fk_item_aktion
                                 FOREIGN KEY (aktion_id)  REFERENCES aktionen(aktion_id)
                                     ON DELETE SET NULL,
                             CONSTRAINT chk_menge
                                 CHECK (menge > 0)
);

-- ================================================
-- 9. GUTSCHEIN USAGE
-- ================================================
CREATE TABLE gutschein_usage (
                                 usage_id        INT AUTO_INCREMENT PRIMARY KEY,
                                 gutschein_id    INT           NOT NULL,
                                 customer_id     INT           NOT NULL,
                                 order_id        INT           NOT NULL,
                                 order_item_id   INT           NOT NULL,
                                 agent_id        INT           NOT NULL,
                                 rabatt_betrag   DECIMAL(10,2) NOT NULL,
                                 used_at         DATETIME      DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT uq_gutschein_customer
                                     UNIQUE (gutschein_id, customer_id),

                                 CONSTRAINT fk_usage_gutschein
                                     FOREIGN KEY (gutschein_id)  REFERENCES gutscheine(gutschein_id),
                                 CONSTRAINT fk_usage_customer
                                     FOREIGN KEY (customer_id)   REFERENCES customers(customer_id),
                                 CONSTRAINT fk_usage_order
                                     FOREIGN KEY (order_id)      REFERENCES orders(order_id),
                                 CONSTRAINT fk_usage_order_item
                                     FOREIGN KEY (order_item_id) REFERENCES order_items(item_id),
                                 CONSTRAINT fk_usage_agent
                                     FOREIGN KEY (agent_id)      REFERENCES users(user_id)
);

-- ================================================
-- 10. PREISANPASSUNGEN
-- ================================================
CREATE TABLE preisanpassungen (
                                  anpassung_id    INT AUTO_INCREMENT PRIMARY KEY,
                                  order_item_id   INT           NOT NULL,
                                  customer_id     INT           NOT NULL,
                                  alter_preis     DECIMAL(10,2) NOT NULL,
                                  neuer_preis     DECIMAL(10,2) NOT NULL,
                                  geaendert_von   INT           NOT NULL,
                                  beschreibung    TEXT          NOT NULL,
                                  geaendert_am    DATETIME      DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_anpassung_order_item
                                      FOREIGN KEY (order_item_id) REFERENCES order_items(item_id),
                                  CONSTRAINT fk_anpassung_customer
                                      FOREIGN KEY (customer_id)   REFERENCES customers(customer_id),
                                  CONSTRAINT fk_anpassung_user
                                      FOREIGN KEY (geaendert_von) REFERENCES users(user_id),

                                  CONSTRAINT chk_neuer_preis
                                      CHECK (neuer_preis < alter_preis)
);