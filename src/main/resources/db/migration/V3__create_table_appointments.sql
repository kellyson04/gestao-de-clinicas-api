CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    status VARCHAR NOT NULL,

    constraint fk_appointments_patient
         foreign key (patient_id) references patients(id),

    constraint fk_appointments_doctor
         foreign key (doctor_id) references doctors(id)
);