CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    appointment_id BIGINT NOT NULL UNIQUE,
    amount DECIMAL(10,2),
    status VARCHAR NOT NULL,

    constraint fk_payment_appointment
        foreign key (appointment_id) references appointments(id)
);