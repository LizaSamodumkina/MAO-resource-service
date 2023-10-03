create table if not exists resource
(
    resource_id serial not null primary key,
    file        bytea  not null,
    checksum    text   not null
)