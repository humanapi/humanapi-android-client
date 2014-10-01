package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Location entity.
 */
public class LocationEntity extends AbstractPeriodicalEntity {

    public LocationEntity(HumanAPIClient client) {
        super(client, "/human/locations");
    }
}
