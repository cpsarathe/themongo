package com.cp.bootmongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

@SpringBootApplication
public class BootMangoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootMangoApplication.class, args);
    }

    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Value("${spring.data.mongodb.auto-index-creation}")
    private boolean canCratedAutoIndex;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public SimpleMongoClientDatabaseFactory simpleMongoClientDatabaseFactory() throws Exception {
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        SimpleMongoClientDatabaseFactory simpleMongoClientDatabaseFactory = new SimpleMongoClientDatabaseFactory(connectionString);
        return simpleMongoClientDatabaseFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        //MongoTemplate mongoTemplate = new MongoTemplate(mongo(), "cpmongodb");
        MongoDatabaseFactory mongoDatabaseFactory = simpleMongoClientDatabaseFactory();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory, getDefaultMongoConverter(mongoDatabaseFactory));
        return mongoTemplate;
    }

    private MongoConverter getDefaultMongoConverter(MongoDatabaseFactory factory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());

        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        mappingContext.afterPropertiesSet();
        mappingContext.setAutoIndexCreation(canCratedAutoIndex);

        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(conversions);
        converter.setCodecRegistryProvider(factory);
        converter.afterPropertiesSet();

        return converter;
    }
}
