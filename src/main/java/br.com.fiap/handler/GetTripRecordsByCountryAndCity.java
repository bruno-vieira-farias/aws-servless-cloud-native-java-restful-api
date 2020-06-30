package br.com.fiap.handler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripRecordsByCountryAndCity implements RequestHandler<HandlerRequest, HandlerResponse> {
    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {
        final String country = request.getPathParameters().get("country");
        final String city = getCity(request);

        context.getLogger().log("Searching for registered trips for " + country);
        final List<Trip> trips = city != null ?
                this.repository.findByCity(country, city):
                this.repository.findByCountry(country);

        if (trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
    }

    private String getCity(HandlerRequest request) {
        try {
            return request.getQueryStringParameters().get("city");
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}