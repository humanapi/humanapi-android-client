package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalMedicationEntity extends AbstractListableEntity {

    public MedicalMedicationEntity(HumanAPIClient client) {
        super(client, "/human/medical/medications");
    }
}
