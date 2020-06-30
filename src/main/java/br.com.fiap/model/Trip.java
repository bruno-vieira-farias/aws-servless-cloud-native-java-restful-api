package br.com.fiap.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "trip")
public class Trip {
    @DynamoDBHashKey(attributeName = "country")
    private String country;

    @DynamoDBRangeKey(attributeName = "date")
    private String date;

    @DynamoDBIndexRangeKey(attributeName = "city", localSecondaryIndexName = "cityIdx")
    private String city;

    @DynamoDBAttribute(attributeName = "reason")
    private String reason;

    public Trip() {
    }

    public Trip(String country, String date, String city, String reason) {
        this.country = country;
        this.date = date;
        this.city = city;
        this.reason = reason;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Contry=%s, Data=%s, City=%s, Reason=%s.", country, date, city, reason);
    }

}