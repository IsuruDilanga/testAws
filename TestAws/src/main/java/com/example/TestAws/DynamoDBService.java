package com.example.TestAws;

import java.util.Map;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBService {

    private final DynamoDbClient dynamoDb;

    public DynamoDBService() {
        this.dynamoDb = DynamoDbClient.builder()
                .region(Region.US_EAST_1) // free-tier region
                .build();
    }

    public void saveUser(String id, String name) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(id).build());
        item.put("name", AttributeValue.builder().s(name).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("Users")
                .item(item)
                .build();

        dynamoDb.putItem(request);
    }

    public Map<String, String>[] fetchUsers() {
        ScanRequest scanRequest = ScanRequest.builder().tableName("Users").build();
        ScanResponse response = dynamoDb.scan(scanRequest);

        return response.items().stream()
                .map(item -> Map.of("id", item.get("id").s(), "name", item.get("name").s()))
                .toArray(Map[]::new);
    }
}
