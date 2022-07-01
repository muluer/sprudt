# sprudt
spring cassandra with user defined type

--> Caused by: com.datastax.oss.driver.api.core.type.codec.CodecNotFoundException: Codec not found for requested operation: [TEXT <-> com.datastax.oss.driver.internal.core.data.DefaultUdtValue]

<-- drop keyspace

---
CREATE KEYSPACE sprudt WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TYPE sprudt.address_udt (
    city text,
    street text,
    zip text
);

CREATE TABLE sprudt.person_table (
    id int PRIMARY KEY,
    current frozen<address_udt>,
    firstname text,
    lastname text,
    previous list<frozen<address_udt>>
)
---
