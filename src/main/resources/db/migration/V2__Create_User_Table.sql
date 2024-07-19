CREATE TABLE "tb_users" (
   "id" integer PRIMARY KEY,
   "role_id" integer,
   "name" varchar(250),
   "email" varchar(250) UNIQUE,
   "password" varchar(250),
   "created_at" timestamp,
   "deleted_at" timestamp
);

ALTER TABLE
"tb_users"
ADD FOREIGN KEY ("role_id") REFERENCES "tb_roles" ("id");
