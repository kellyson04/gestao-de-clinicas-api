ALTER TABLE doctors
ADD CONSTRAINT uk_doctors_crm_number UNIQUE(crm_number);