package io.searchbox.cluster;

import com.google.gson.JsonObject;
import io.searchbox.client.JestResult;
import io.searchbox.common.AbstractIntegrationTest;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author cihat keser
 */
@ElasticsearchIntegrationTest.ClusterScope(scope = ElasticsearchIntegrationTest.Scope.GLOBAL, numDataNodes = 1)
public class StateIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void clusterState() throws IOException {
        JestResult result = client.execute(new State.Builder().build());
        assertTrue(result.getErrorMessage(), result.isSucceeded());

        JsonObject resultJson = result.getJsonObject();
        assertNotNull(resultJson);
        assertNotNull(resultJson.getAsJsonObject("nodes"));
        assertNotNull(resultJson.getAsJsonObject("routing_table"));
        assertNotNull(resultJson.getAsJsonObject("metadata"));
        assertNotNull(resultJson.getAsJsonObject("blocks"));
    }

    @Test
    public void clusterStateWithMetadata() throws IOException {
        JestResult result = client.execute(new State.Builder().filterMetadata(true).build());
        assertTrue(result.getErrorMessage(), result.isSucceeded());

        JsonObject resultJson = result.getJsonObject();
        assertNotNull(resultJson);
        assertNull(resultJson.getAsJsonObject("nodes"));
        assertNull(resultJson.getAsJsonObject("routing_table"));
        assertNotNull(resultJson.getAsJsonObject("metadata"));
        assertNull(resultJson.getAsJsonObject("blocks"));
    }

}
