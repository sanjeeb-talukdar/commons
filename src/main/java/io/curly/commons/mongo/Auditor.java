package io.curly.commons.mongo;

import org.joda.time.DateTime;
import org.springframework.data.annotation.*;

/**
 * @author João Pedro Evangelista
 */
public class Auditor {

    @Version
    private Long version;

    @CreatedDate
    private DateTime createdAt;

    @LastModifiedDate
    private DateTime lastModifiedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;


}
