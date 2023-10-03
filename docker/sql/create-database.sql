create user "resource_owner" nosuperuser nocreatedb nocreaterole encrypted password 'abc';

create database resource_db;

alter database resource_db owner to resource_owner;

\c resource_db;

create schema "resource_schema";
grant usage on schema resource_schema to resource_owner;
grant create on schema resource_schema to resource_owner;
alter default privileges in schema resource_schema grant all privileges on tables to resource_owner;
