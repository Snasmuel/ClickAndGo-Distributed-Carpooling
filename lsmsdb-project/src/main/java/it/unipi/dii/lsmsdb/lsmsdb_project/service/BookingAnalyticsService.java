package it.unipi.dii.lsmsdb.lsmsdb_project.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class BookingAnalyticsService {

    @Autowired private MongoTemplate mongoTemplate;

    public Document getRevenueStats(String start, String end) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("bookingDate").gte(start).lte(end)),
                Aggregation.group().sum("finalPrice").as("totalRevenue")
                        .count().as("totalConfirmedBookings")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "bookings", Document.class);
        return results.getUniqueMappedResult();
    }

    public List<Document> getHighValueChurners(int daysThreshold) {
        MatchOperation filterRated = Aggregation.match(Criteria.where("reviews_passanger.average_rating").gte(4.0));
        LookupOperation lookupBookings = LookupOperation.newLookup()
                .from("bookings")
                .localField("_id")
                .foreignField("passenger.id")
                .as("ub");
        ProjectionOperation projectFields = Aggregation.project("personalInfo.name")
                .and("reviews_passanger.average_rating").as("rating")
                .and(AccumulatorOperators.Sum.sumOf("ub.finalPrice")).as("totalSpent");

        Aggregation agg = Aggregation.newAggregation(
                filterRated,
                Aggregation.limit(100), // only 100 documents for optimization
                lookupBookings,
                projectFields,
                Aggregation.sort(Sort.Direction.DESC, "totalSpent"),
                Aggregation.limit(10)
        );
        return mongoTemplate.aggregate(agg, "users", Document.class).getMappedResults();
    }

    public List<Document> getTopDriverLeaderboard() {
        // Calculate trust points based on documents
        AddFieldsOperation trustPointsStage = Aggregation.addFields()
                .addFieldWithValue("trustPoints",
                        ConditionalOperators.when(Criteria.where("personalInfo.is_identity_verified").is(true))
                                .then(50).otherwise(0)
                ).build();

        // Calculate final score: Trust * Rating * Acceptance
        ProjectionOperation projectStage = Aggregation.project("personalInfo.name")
                .and("reviews_driver.average_rating").as("rating")
                .and("driverInfo.avg_acceptance_rate").as("acceptanceRate")
                .and(
                        ArithmeticOperators.Multiply.valueOf("trustPoints")
                                .multiplyBy(ConditionalOperators.ifNull("reviews_driver.average_rating").then(1))
                                .multiplyBy(ConditionalOperators.ifNull("driverInfo.avg_acceptance_rate").then(0.5))
                ).as("performanceScore");

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("driverInfo.number_of_acceptance").gt(0)),
                trustPointsStage,
                projectStage,
                Aggregation.sort(Sort.Direction.DESC, "performanceScore"),
                Aggregation.limit(10)
        );

        return mongoTemplate.aggregate(agg, "users", Document.class).getMappedResults();
    }
}