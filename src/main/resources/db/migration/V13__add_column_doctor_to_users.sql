ALTER TABLE users
ADD COLUMN doctor_id BIGINT UNIQUE,

ADD CONSTRAINT fk_users_doctors
FOREIGN KEY (doctor_id)
REFERENCES doctors(id);
