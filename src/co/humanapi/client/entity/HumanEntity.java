package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;
import us.monoid.json.JSONObject;

/**
 * Human entity.
 */
public class HumanEntity extends AbstractEntity {

    public HumanEntity(HumanAPIClient client) {
        super(client);
    }

    public JSONObject get() throws HumanAPIException {
        return client.execute("/human/");
    }
}
