CREATE ROLE super WITH LOGIN PASSWORD 'super';

CREATE ROLE superman WITH LOGIN PASSWORD 'superman' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
CREATE DATABASE logging_database;
GRANT ALL PRIVILEGES ON DATABASE logging_database TO superman ;
GRANT ALL PRIVILEGES ON DATABASE logging_database TO super;