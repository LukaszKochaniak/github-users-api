CREATE TABLE IF NOT EXISTS user (
    login varchar(255) PRIMARY KEY NOT NULL,
    request_count bigint DEFAULT 0 NOT NULL,
    version bigint DEFAULT 0 NOT NULL,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    last_update_date timestamp without time zone
);