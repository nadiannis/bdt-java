DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS account_info CASCADE;
DROP TABLE IF EXISTS bank_info CASCADE;

CREATE TABLE accounts (
    id bigserial,
    account_number varchar(255),
    balance numeric,
    CONSTRAINT "accounts_pkey" PRIMARY KEY (id)
);

CREATE TABLE account_info (
    id bigserial,
    account_id bigint NULL,
    account_name varchar(255) NULL,
    CONSTRAINT "account_info_pkey" PRIMARY KEY (id),
    CONSTRAINT "account_info_account_id_fkey"
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
        ON DELETE CASCADE
);

CREATE TABLE bank_info (
    id bigserial,
    account_id bigint NULL,
    bank_name varchar(255) NULL,
    CONSTRAINT "bank_info_pkey" PRIMARY KEY (id),
    CONSTRAINT "bank_info_account_id_fkey"
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
        ON DELETE CASCADE
);

INSERT INTO accounts (id, account_number, balance) VALUES (1, '1234567890', 10000);
INSERT INTO accounts (id, account_number, balance) VALUES (2, '0123456789', 20000);

INSERT INTO account_info (account_id, account_name) VALUES (2, 'Account Name 1');
INSERT INTO account_info (account_id, account_name) VALUES (1, 'Account Name 2');

INSERT INTO bank_info (account_id, bank_name) VALUES (1, 'Bank 1');
INSERT INTO bank_info (account_id, bank_name) VALUES (2, 'Bank 2');
