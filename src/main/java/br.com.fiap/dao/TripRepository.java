package br.com.fiap.dao;

import br.com.fiap.model.Trip;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository {
    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip trip) {
        mapper.save(trip);
        return trip;
    }

    public List<Trip> findByPeriod(final String startDate, final String endDate) {
        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":startDate", new AttributeValue().withS(startDate));
        eav.put(":endDate", new AttributeValue().withS(endDate));

        final Map<String, String> expression = new HashMap<>();
        expression.put("#date", "date");

        final DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("#date between :startDate and :endDate")
                .withExpressionAttributeValues(eav)
                .withExpressionAttributeNames(expression);
        final List<Trip> result = mapper.scan(Trip.class, queryExpression);

        return result;
    }

    public List<Trip> findByCountry(final String country) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country = :val1").withExpressionAttributeValues(eav);

        final List<Trip> result = mapper.query(Trip.class, queryExpression);

        return result;
    }

    public List<Trip> findByCity(final String country, final String city) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));
        eav.put(":val2", new AttributeValue().withS(city));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country = :val1")
                .withFilterExpression("contains (city, :val2)")
                .withExpressionAttributeValues(eav);

        final List<Trip> result = mapper.query(Trip.class, queryExpression);

        return result;
    }
}
