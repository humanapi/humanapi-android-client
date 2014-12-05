package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Height entity.
 */
public class HeightEntity extends AbstractMeasurementEntity {

    public HeightEntity(HumanAPIClient client) {
        super(client, "/human/height");
    }
}
