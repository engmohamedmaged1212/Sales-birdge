ALTER TABLE customers
    ADD COLUMN gender ENUM('MALE', 'FEMALE') AFTER date_of_birth;