package com.revature.get_games;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRepository {

    private static final GameRepository gameRepository = new GameRepository();
    private final DynamoDBMapper dbReader;

    private GameRepository() {
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public static GameRepository getInstance() {
        return gameRepository;
    }

    @SneakyThrows
    public List<Game> getAllGames() {
        return dbReader.scan(Game.class, new DynamoDBScanExpression());
    }

    @SneakyThrows
    public List<Game> getGameByTitle(String title) {
        Map<String, AttributeValue> queryInputs = new HashMap<>();
        queryInputs.put(":title", new AttributeValue().withS(title));

        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("title = :title")
                .withExpressionAttributeValues(queryInputs);

        List<Game> queryResult = dbReader.scan(Game.class, query);
        return queryResult;
    }
}
