ALTER TABLE payments
ADD CONSTRAINT payments_appointment_id_key UNIQUE (appointment_id);