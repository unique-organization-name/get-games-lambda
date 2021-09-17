package com.revature.get_games;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class GetGamesHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final GameRepository gameRepository = GameRepository.getInstance();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        LambdaLogger logger = context.getLogger();
        logger.log("RECEIVED EVENT: " + requestEvent);

        Map<String, String> pathParameters = requestEvent.getQueryStringParameters();

        if (pathParameters == null) {
            responseEvent.setBody(mapper.toJson(gameRepository.getAllGames()));
            return responseEvent;
        }

        responseEvent.setBody(mapper.toJson(gameRepository.getGameByTitle(pathParameters.get("title"))));
        return responseEvent;
    }
}
