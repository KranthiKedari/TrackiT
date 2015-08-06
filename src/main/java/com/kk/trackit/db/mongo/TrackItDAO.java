package com.kk.trackit.db.mongo;

import com.kk.trackit.dto.UserSettings;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by kkedari on 7/13/15.
 */
public class TrackItDAO extends BasicDAO<UserSettings, ObjectId> {

    private MongoTemplate mongoTemplate;

    public TrackItDAO(MongoClient mongoClient, Morphia morphia, String dbName, MongoTemplate mongoTemplate) {
        super(mongoClient, morphia, dbName);
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }

    public UserSettings getCurrentSettings(String userId) {
        Query query = new Query();

        query.addCriteria(new Criteria("userId").is(userId));
        Datastore ds = this.getDatastore();
        UserSettings userSettings = ds.find(UserSettings.class).field("userId").equal(userId ).get();

        return  userSettings;
    }
}
