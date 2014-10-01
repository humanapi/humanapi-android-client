package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;
import us.monoid.json.JSONObject;

/**
 * Profile entity.
 */
public class ProfileEntity extends AbstractEntity {

    public ProfileEntity(HumanAPIClient client) {
        super(client);
    }

    public JSONObject get() throws HumanAPIException {
        return client.execute("/human/profile");
    }
}
