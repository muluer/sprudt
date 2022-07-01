package com.uluer.demo.sprudt.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uluer.demo.sprudt.model.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories
public class SprUdtCassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDataCenter;

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.base-packages}")
    private String[] basePackages;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDataCenter;
    }

    @Override
    public String[] getEntityBasePackages() {
        return basePackages;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification createKeyspaceSpecification = CreateKeyspaceSpecification
                .createKeyspace(keyspaceName)
                .ifNotExists()
                .withSimpleReplication()
                .with(KeyspaceOption.DURABLE_WRITES, true);
        return Arrays.asList(createKeyspaceSpecification);
    }

    @Override
    public CassandraCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new AddressReadConverter());
        converters.add(new AddressWriteConverter());
        return new CassandraCustomConversions(converters);
    }

    @ReadingConverter
    public class AddressReadConverter implements Converter<String, Address> {
        @Override
        public Address convert(String source) {
            Address address = null;
            try {
                address = new ObjectMapper().reader().readValue(source, Address.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return address;
        }
    }

    @WritingConverter
    public class AddressWriteConverter implements Converter<Address, String> {
        @Override
        public String convert(Address source) {
            String addrStr = null;
            try {
                addrStr = new ObjectMapper().writer().writeValueAsString(source);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return addrStr;
        }
    }

}
