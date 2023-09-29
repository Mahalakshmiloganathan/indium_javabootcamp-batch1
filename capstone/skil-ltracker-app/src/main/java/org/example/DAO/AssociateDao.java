package org.example.DAO;

import org.example.model.Associate;
import org.example.service.SkillService;

import java.util.List;

public interface AssociateDao {
    void addAssociate(Associate associate);

    void editAssociate(int associateId, Associate updatedAssociate);

    void deleteAssociate(int associateId);

    List<Associate> listAssociates(SkillService skillService);

    Associate findAssociateById(int associateId, SkillService skillService);

    List<Associate> searchAssociates(String criteria, SkillService skillService);
}
