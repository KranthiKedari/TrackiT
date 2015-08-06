package com.kk.trackit;

import com.kk.trackit.db.mongo.TrackItDAO;
import com.kk.trackit.dto.UserSettings;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by kkedari on 7/13/15.
 */
@Configuration
public class TrackItConfig {
        @Value("${mongo.server}")
        private String mMongoServer;

        @Value("${mongo.port}")
        private int mMongoPort;

        @Value("${mongo.database}")
        private String databaseName;

        @Bean
        public MongoTemplate template() throws IOException {
            return new MongoTemplate(mongo(), databaseName);
        }

        @Bean
        public MongoClient mongo() throws UnknownHostException {
            return new MongoClient(mMongoServer, mMongoPort);
        }


        @Bean
        public TrackItDAO trackItDAO() throws UnknownHostException, IOException {
            Morphia morphia = new Morphia();
            morphia.map(UserSettings.class);
            morphia.getMapper().getOptions().setStoreEmpties( true);

            return new TrackItDAO(mongo(), morphia, databaseName, template());
        }
}
