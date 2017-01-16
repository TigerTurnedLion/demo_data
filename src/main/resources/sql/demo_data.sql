CREATE SCHEMA align;

DROP TABLE IF EXISTS align.members;
CREATE TABLE align.members(
  id SERIAL,
  id_member VARCHAR,
  birth_date DATE,
  gender_code CHAR,
  name_first VARCHAR,
  name_last VARCHAR,
  membership_date DATE,
  region VARCHAR,
  group_id VARCHAR,
  office VARCHAR,
  new_member VARCHAR,
  LOB VARCHAR
);

DROP TABLE IF EXISTS align.providers;
CREATE TABLE align.providers(
  id SERIAL,
  ordering_physician_id VARCHAR,
  provider_name_first VARCHAR,
  provider_name_last VARCHAR,
  ordering_physician_dea VARCHAR,
  specialty_code VARCHAR,
  network_participant BOOLEAN
);

DROP TABLE IF EXISTS align.claims;
CREATE TABLE align.claims(
  id SERIAL,
  pharmacy_claim_nbr VARCHAR,
  id_number VARCHAR,
  ordering_physician_id VARCHAR,
  ordering_physician_dea VARCHAR,
  pharmacy_id VARCHAR,
  prescription_nbr VARCHAR,
  refill_code CHAR,
  ndc VARCHAR,
  date_paid DATE,
  date_filled DATE,
  brand_or_generic CHAR,
  amount_paid DECIMAL,
  ingredient_cost DECIMAL,
  dispensing_fee DECIMAL,
  copay_amt DECIMAL,
  deductible_amount DECIMAL,
  disallowed_amount DECIMAL,
  awp DECIMAL,
  quantity DECIMAL,
  day_supply INT
);

DROP TABLE IF EXISTS align.firstnames;
CREATE TABLE align.firstnames(
  id SERIAL,
  firstname VARCHAR
);

DROP TABLE IF EXISTS align.lastnames;
CREATE TABLE align.lastnames(
  id SERIAL,
  lastname VARCHAR
);

DROP TABLE IF EXISTS align.hash_members;
CREATE TABLE align.hash_members(
  member_id_hash VARCHAR,
  birth_date DATE,
  gender_code CHAR,
  name_first VARCHAR,
  name_last VARCHAR,
  membership_date DATE,
  region VARCHAR,
  group_id VARCHAR,
  office VARCHAR,
  new_member VARCHAR,
  LOB VARCHAR
);

DROP TABLE IF EXISTS align.hash_providers;
CREATE TABLE align.hash_providers(
  physician_id_hash VARCHAR,
  provider_name_first VARCHAR,
  provider_name_last VARCHAR,
  ordering_physician_dea VARCHAR,
  specialty_code VARCHAR,
  network_participant BOOLEAN
);

DROP TABLE IF EXISTS align.hash_claims;
CREATE TABLE align.hash_claims(
  pharmacy_claim_nbr VARCHAR,
  id_number VARCHAR,
  ordering_physician_id VARCHAR,
  ordering_physician_dea VARCHAR,
  pharmacy_id VARCHAR,
  prescription_nbr VARCHAR,
  refill_code CHAR,
  ndc VARCHAR,
  date_paid DATE,
  date_filled DATE,
  brand_or_generic CHAR,
  amount_paid DECIMAL,
  ingredient_cost DECIMAL,
  dispensing_fee DECIMAL,
  copay_amt DECIMAL,
  deductible_amount DECIMAL,
  disallowed_amount DECIMAL,
  awp DECIMAL,
  quantity DECIMAL,
  day_supply INT
);

SELECT COUNT(1) AS member_count FROM align.members;
SELECT COUNT(1) AS hash_member_count FROM align.hash_members;

TRUNCATE align.hash_members;