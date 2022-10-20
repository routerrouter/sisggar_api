CREATE TABLE IF NOT EXISTS TB_LOCATION (
	location_id UUID DEFAULT gen_random_uuid (),
	description VARCHAR(180) NOT NULL,
	location_status VARCHAR(180) NOT NULL,
	creation_date TIMESTAMP,
	last_update_date TIMESTAMP,
	storage_id UUID,
	PRIMARY KEY (location_id),
	CONSTRAINT fk_storage
      FOREIGN KEY(storage_id)
	  REFERENCES tb_storage(storage_id)
);