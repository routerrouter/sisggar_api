CREATE TABLE IF NOT EXISTS TB_DELIVERYMANAGER (
	delivery_manager_id UUID DEFAULT gen_random_uuid (),
	full_name VARCHAR(180) NOT NULL,
	phone_number VARCHAR(180) NOT NULL,
	status VARCHAR(180) NOT NULL,
	image_url VARCHAR(500) NOT NULL,
	creation_date TIMESTAMP,
	last_update_date TIMESTAMP,
	storage_id UUID,
	PRIMARY KEY (delivery_manager_id),
	CONSTRAINT fk_storage
      FOREIGN KEY(storage_id)
	  REFERENCES tb_storage(storage_id)
);