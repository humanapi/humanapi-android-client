package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Weight entity.
 */
public class WeightEntity extends AbstractMeasurementEntity {

    public WeightEntity(HumanAPIClient client) {
        super(client, "/human/weight");
    }
}
