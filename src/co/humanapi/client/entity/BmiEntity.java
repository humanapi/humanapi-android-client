package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * BMI entity.
 */
public class BmiEntity extends AbstractMeasurementEntity {

    public BmiEntity(HumanAPIClient client) {
        super(client, "/human/bmi");
    }
}
