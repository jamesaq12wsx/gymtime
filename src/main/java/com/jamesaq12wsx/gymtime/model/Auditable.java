package com.jamesaq12wsx.gymtime.model;

import com.jamesaq12wsx.gymtime.model.entity.Audit;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);

//    String getCreatedBy();
//
//    LocalDateTime getCreatedAt();
//
//    LocalDateTime getUpdatedAt();

}
