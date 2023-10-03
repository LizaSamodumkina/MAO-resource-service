FROM postgres:latest
COPY sql/create-database.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
CMD ["postgres"]
