package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Activity entity.
 */
public class ActivityEntity extends AbstractPeriodicalEntity {

    public ActivityEntity(HumanAPIClient client) {
        super(client, "/human/activities");
    }
}
