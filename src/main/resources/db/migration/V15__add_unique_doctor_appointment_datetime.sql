ALTER TABLE appointments
ADD CONSTRAINT uk_appointments_doctor_date_time UNIQUE (doctor_id, date_time);
