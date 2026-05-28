ALTER TABLE users
ADD COLUMN patient_id BIGINT UNIQUE ,

ADD CONSTRAINT fk_users_patients
FOREIGN KEY (patient_id)
REFERENCES patients(id);