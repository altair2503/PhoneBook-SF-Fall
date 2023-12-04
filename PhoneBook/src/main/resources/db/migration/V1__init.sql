CREATE TABLE IF NOT EXISTS public.users(
   id INTEGER PRIMARY KEY,
   email VARCHAR(255),
   password VARCHAR(255),
   first_name  VARCHAR(255),
   last_name VARCHAR(255),
   phone_number varchar(255)
);