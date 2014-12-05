package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Blood pressure entity.
 */
public class BloodPressureEntity extends AbstractMeasurementEntity {

    public BloodPressureEntity(HumanAPIClient client) {
        super(client, "/human/blood_pressure");
    }
}
