package br.com.fiap.handler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripRecordsByCountry implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {
        final String country = request.getPathParameters().get("country");
        final String city = getCity(request);

        context.getLogger().log("Searching for registered trips for ->" + country);
        final List<Trip> trips = getTrips(country, city);

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
    }

    private List<Trip> getTrips(String country, String city) {
        if (city == null) {
            return this.repository.findByCountry(country);
        }

        return this.repository.findByCity(country, city);
    }

    private String getCity(HandlerRequest request) {
        try {
            return request.getQueryStringParameters().get("city");
        } catch (NullPointerException e) {
            return null;
        }
    }
}