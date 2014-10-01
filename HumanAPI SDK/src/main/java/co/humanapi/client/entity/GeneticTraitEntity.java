package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;

/**
 * Genetic trait entity.
 */
public class GeneticTraitEntity extends AbstractEntity {

    public GeneticTraitEntity(HumanAPIClient client) {
        super(client);
    }

    public JSONArray list() throws HumanAPIException {
        return client.executeForArray("/human/genetic/traits");
    }
}
