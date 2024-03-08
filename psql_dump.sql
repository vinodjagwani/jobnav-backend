
CREATE SCHEMA jobnav;
CREATE TABLE jobnav.job (
	job_id varchar(255) NOT NULL,
	created_by_user varchar(36) NULL,
	created_datetime timestamp NULL,
	updated_by_user varchar(36) NULL,
	updated_datetime timestamp NULL,
	career_path varchar(255) NOT NULL,
	company varchar(255) NOT NULL,
	description varchar(255) NOT NULL,
	job_type varchar(255) NOT NULL,
	"location" varchar(255) NOT NULL,
	"position" varchar(255) NOT NULL,
	title varchar(255) NOT NULL,
	CONSTRAINT job_pkey PRIMARY KEY (job_id)
);


-- jobnav.job_applicant definition

-- Drop table

-- DROP TABLE jobnav.job_applicant;

CREATE TABLE jobnav.job_applicant (
	job_applicant_id varchar(255) NOT NULL,
	created_by_user varchar(36) NULL,
	created_datetime timestamp NULL,
	updated_by_user varchar(36) NULL,
	updated_datetime timestamp NULL,
	email varchar(255) NOT NULL,
	is_enabled bool NULL,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT job_applicant_pkey PRIMARY KEY (job_applicant_id),
	CONSTRAINT uk8mfk70n2msmtaeyhfvpg4eavu UNIQUE (username, email)
);


-- jobnav.job_applicant_contact_form definition

-- Drop table

-- DROP TABLE jobnav.job_applicant_contact_form;

CREATE TABLE jobnav.job_applicant_contact_form (
	job_applicant_contact_form_id varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	message text NOT NULL,
	phone varchar(255) NOT NULL,
	CONSTRAINT job_applicant_contact_form_pkey PRIMARY KEY (job_applicant_contact_form_id)
);


-- jobnav.job_mailing definition

-- Drop table

-- DROP TABLE jobnav.job_mailing;

CREATE TABLE jobnav.job_mailing (
	job_mailing_id varchar(255) NOT NULL,
	created_by_user varchar(36) NULL,
	created_datetime timestamp NULL,
	updated_by_user varchar(36) NULL,
	updated_datetime timestamp NULL,
	email varchar(255) NOT NULL,
	CONSTRAINT job_mailing_pkey PRIMARY KEY (job_mailing_id),
	CONSTRAINT uk_m0tjetb07pnvmimih8jouoa0p UNIQUE (email)
);


-- jobnav.job_applicant_profile definition

-- Drop table

-- DROP TABLE jobnav.job_applicant_profile;

CREATE TABLE jobnav.job_applicant_profile (
	job_applicant_profile_id varchar(255) NOT NULL,
	created_by_user varchar(36) NULL,
	created_datetime timestamp NULL,
	updated_by_user varchar(36) NULL,
	updated_datetime timestamp NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	mobile varchar(255) NOT NULL,
	resume_json text NULL,
	job_applicant_id varchar(255) NULL,
	CONSTRAINT job_applicant_profile_pkey PRIMARY KEY (job_applicant_profile_id),
	CONSTRAINT fklsnu4q4ynj7wylh5mgu04w1gm FOREIGN KEY (job_applicant_id) REFERENCES jobnav.job_applicant(job_applicant_id) ON DELETE CASCADE
);


-- jobnav.job_skills definition

-- Drop table

-- DROP TABLE jobnav.job_skills;

CREATE TABLE jobnav.job_skills (
	job_id varchar(255) NOT NULL,
	skills varchar(255) NOT NULL,
	CONSTRAINT fkqktpavcbch6i6k65kcc0xq029 FOREIGN KEY (job_id) REFERENCES jobnav.job(job_id)
);
