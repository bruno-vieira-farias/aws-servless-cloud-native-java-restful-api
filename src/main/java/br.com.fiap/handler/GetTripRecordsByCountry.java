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

        context.getLogger().log("Searching for registered trips for " + country);

        final List<Trip> trips = this.repository.findByCountry(country);

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
    }
}