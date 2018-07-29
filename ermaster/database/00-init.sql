--
-- Initialize
--

DROP DATABASE IF EXISTS stex;
DROP DATABASE IF EXISTS "stex-test";
DROP ROLE IF EXISTS stex;
DROP ROLE IF EXISTS "stex-test";


--
-- Roles
--

CREATE ROLE stex WITH
  SUPERUSER
  INHERIT
  LOGIN
  PASSWORD 'stex';

CREATE ROLE "stex-test" WITH
  SUPERUSER
  INHERIT
  LOGIN
  PASSWORD 'stex';


--
-- Databases
--

CREATE DATABASE stex WITH
    TEMPLATE = template0
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    CONNECTION LIMIT = -1;

CREATE DATABASE "stex-test" WITH
    TEMPLATE = template0
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    CONNECTION LIMIT = -1;


ALTER DATABASE stex OWNER TO stex;
ALTER DATABASE "stex-test" OWNER TO "stex-test";
