package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;

/**
 * Base class for data types that organized in lists with
 * possibility to access details of each object from the same end point.
 * (List queries can be filtered)
 *
 * Used for medical end point for now but not limited to them.
 */
public class AbstractListableEntity extends AbstractEntity {

    /** Path to the entity root (prefix) */
    protected String master;

    protected AbstractPeriodicalEntity(HumanAPIClient client, String master) {
        super(client);
        this.master = master;
    }

    public JSONArray list() throws HumanAPIException {
        return client.executeForArray(master + "/");
    }

    public JSONObject get(String id) throws HumanAPIException {
        return client.execute(master + "/" + id);
    }
}
