package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;

/**
 * Allergy entity.
 */
public class MedicalIssueEntity extends AbstractListableEntity {

    public MedicalIssueEntity(HumanAPIClient client) {
        super(client, "/human/medical/issues");
    }
}
