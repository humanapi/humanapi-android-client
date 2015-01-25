package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalImmunizationEntity extends AbstractListableEntity {

    public MedicalImmunizationEntity(HumanAPIClient client) {
        super(client, "/human/medical/immunizations");
    }
}
