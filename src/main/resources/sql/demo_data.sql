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
  ordering_physician_dea_hash VARCHAR,
  specialty_code VARCHAR,
  network_participant BOOLEAN
);

DROP TABLE IF EXISTS align.hash_claims;
CREATE TABLE align.hash_claims(
  pharmacy_claim_nbr VARCHAR,
  member_id_hash VARCHAR,
  physician_id_hash VARCHAR,
  physician_dea_hash VARCHAR,
  pharmacy_id_hash VARCHAR,
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
SELECT COUNT(1) AS provider_count FROM align.providers;
SELECT COUNT(1) AS claim_count FROM align.claims;

SELECT COUNT(1) AS hash_member_count FROM align.hash_members;

SELECT COUNT(1) AS hash_provider_count FROM align.hash_providers;
SELECT COUNT(1) AS hash_claim_count FROM align.hash_claims;

TRUNCATE align.hash_members;

SELECT * FROM align.providers WHERE ordering_physician_dea IS NOT NULL LIMIT 5;

SELECT * FROM align.claims WHERE pharmacy_id IS NOT NULL LIMIT 5;
SELECT * FROM align.claims WHERE pharmacy_id IS NULL LIMIT 5;



SELECT * FROM align.members LIMIT 10;

SELECT * FROM align.hash_members;
SELECT * FROM align.members LIMIT 10;

SELECT * FROM align.hash_providers;

SELECT * FROM align.hash_claims;

SELECT id_member FROM align.members ORDER BY id;

SELECT * FROM align.hash_members;


DROP TABLE IF EXISTS align.hash_members_bak;
CREATE TABLE align.hash_members_bak(
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

INSERT INTO align.hash_members_bak(
  member_id_hash,
  birth_date,
  gender_code,
  name_first,
  name_last,
  membership_date,
  region,
  group_id,
  office,
  new_member,
  LOB
)
  SELECT
    member_id_hash,
    birth_date,
    gender_code,
    name_first,
    name_last,
    membership_date,
    region,
    group_id,
    office,
    new_member,
    LOB
  FROM
    align.hash_members;

SELECT COUNT(1) FROM align.hash_members_bak;

SELECT * FROM align.hash_members;

SELECT * FROM align.hash_providers;

SELECT * FROM align.hash_claims;

DROP TABLE IF EXISTS tmp_hash_members;
CREATE TEMP TABLE tmp_hash_members(
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

INSERT INTO tmp_hash_members(
  member_id_hash,
  birth_date,
  gender_code,
  name_first,
  name_last,
  membership_date,
  region,
  group_id,
  office,
  new_member,
  LOB
)
  SELECT DISTINCT
    member_id_hash,
    birth_date,
    gender_code,
    name_first,
    name_last,
    membership_date,
    region,
    group_id,
    office,
    new_member,
    LOB
  FROM
    align.hash_members
  LIMIT 50000;

DROP TABLE IF EXISTS tmp_hash_claims;
CREATE TABLE tmp_hash_claims(
  pharmacy_claim_nbr VARCHAR,
  member_id_hash VARCHAR,
  physician_id_hash VARCHAR,
  physician_dea_hash VARCHAR,
  pharmacy_id_hash VARCHAR,
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

INSERT INTO tmp_hash_claims(
  pharmacy_claim_nbr,
  member_id_hash,
  physician_id_hash,
  physician_dea_hash,
  pharmacy_id_hash,
  prescription_nbr,
  refill_code,
  ndc,
  date_paid,
  date_filled,
  brand_or_generic,
  amount_paid,
  ingredient_cost,
  dispensing_fee,
  copay_amt,
  deductible_amount,
  disallowed_amount,
  awp,
  quantity,
  day_supply
)
SELECT DISTINCT
  pharmacy_claim_nbr,
  c.member_id_hash,
  physician_id_hash,
  physician_dea_hash,
  pharmacy_id_hash,
  prescription_nbr,
  refill_code,
  ndc,
  date_paid,
  date_filled,
  brand_or_generic,
  amount_paid,
  ingredient_cost,
  dispensing_fee,
  copay_amt,
  deductible_amount,
  disallowed_amount,
  awp,
  quantity,
  day_supply
FROM
  align.hash_claims c
  INNER JOIN tmp_hash_members m ON m.member_id_hash = c.member_id_hash;


DROP TABLE IF EXISTS tmp_hash_providers;
CREATE TABLE tmp_hash_providers(
  physician_id_hash VARCHAR,
  provider_name_first VARCHAR,
  provider_name_last VARCHAR,
  ordering_physician_dea_hash VARCHAR,
  specialty_code VARCHAR,
  network_participant BOOLEAN
);

INSERT INTO tmp_hash_providers(
  physician_id_hash,
  provider_name_first,
  provider_name_last,
  ordering_physician_dea_hash,
  specialty_code,
  network_participant
)
    SELECT DISTINCT
      p.physician_id_hash,
      provider_name_first,
      provider_name_last,
      ordering_physician_dea_hash,
      specialty_code,
      network_participant
  FROM
    align.hash_providers p
    INNER JOIN tmp_hash_claims c ON c.physician_id_hash = p.physician_id_hash;


SELECT * FROM tmp_hash_members;

SELECT * FROM tmp_hash_providers;

SELECT * FROM tmp_hash_claims;