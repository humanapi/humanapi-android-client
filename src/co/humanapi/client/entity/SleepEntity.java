package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Sleep entity.
 */
public class SleepEntity extends AbstractPeriodicalEntity {

    public SleepEntity(HumanAPIClient client) {
        super(client, "/human/sleeps");
    }
}
