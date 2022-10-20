CREATE TABLE IF NOT EXISTS TB_STORAGE (
	storage_id UUID DEFAULT gen_random_uuid (),
	storage_limit INTEGER NOT NULL,
	description VARCHAR(180) NOT NULL,
	storage_status VARCHAR(180) NOT NULL,
	creation_date TIMESTAMP,
	last_update_date TIMESTAMP,
	PRIMARY KEY (storage_id)
);