package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalEncounterEntity extends AbstractListableEntity {

    public MedicalEncounterEntity(HumanAPIClient client) {
        super(client, "/human/medical/encounters");
    }
}
