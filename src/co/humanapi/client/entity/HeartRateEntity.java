package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Heart rate entity.
 */
public class HeartRateEntity extends AbstractMeasurementEntity {

    public HeartRateEntity(HumanAPIClient client) {
        super(client, "/human/heart_rate");
    }
}
