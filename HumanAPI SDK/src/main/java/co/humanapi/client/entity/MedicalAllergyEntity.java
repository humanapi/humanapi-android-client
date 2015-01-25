package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalAllergyEntity extends AbstractListableEntity {

    public MedicalAllergyEntity(HumanAPIClient client) {
        super(client, "/human/medical/allergies");
    }
}
