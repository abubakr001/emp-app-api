package rnd.net.aws.emp.svc.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AwsDynamoDbServiceClient {

    private static final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    static {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .build();
        dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    public <T> void addItem(String tableName, Object obj, Class<T> aClass) {
        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(aClass));
        dynamoDbTable.putItem((T)obj);
    }

    public <T> void updateItem(String tableName, Object obj, Class<T> aClass) {
        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(aClass));
        dynamoDbTable.updateItem((T)obj);
    }

    public <T>  void deleteItem(String tableName, Object obj, Class<T> aClass) {
        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(aClass));
        dynamoDbTable.deleteItem((T)obj);
    }

    public <T> List<T> getAllItems(String tableName, Class<T> aClass) {
        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(aClass));
        return dynamoDbTable.scan().items().stream().collect(Collectors.toList());
    }

    public <T> T getItemById(String tableName, Object obj, Class<T> aClass) {
        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(aClass));
        return dynamoDbTable.getItem((T)obj);
    }
}
