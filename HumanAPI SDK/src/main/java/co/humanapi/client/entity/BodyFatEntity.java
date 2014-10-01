package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Body fat entity.
 */
public class BodyFatEntity extends AbstractMeasurementEntity {

    public BodyFatEntity(HumanAPIClient client) {
        super(client, "/human/body_fat");
    }
}
