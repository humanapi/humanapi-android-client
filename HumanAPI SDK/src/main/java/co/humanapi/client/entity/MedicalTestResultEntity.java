package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalTestResultEntity extends AbstractListableEntity {

    public MedicalTestResultEntity(HumanAPIClient client) {
        super(client, "/human/medical/test_results");
    }
}
