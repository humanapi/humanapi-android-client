package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Blood glucose entity.
 */
public class BloodGlucoseEntity extends AbstractMeasurementEntity {

    public BloodGlucoseEntity(HumanAPIClient client) {
        super(client, "/human/blood_glucose");
    }
}
