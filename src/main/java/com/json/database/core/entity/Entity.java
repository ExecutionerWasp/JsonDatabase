package com.json.database.core.entity;

import java.io.Serializable;

/**
 * @author Alvin
 **/
@FunctionalInterface
public interface Entity<ID extends Serializable> {

    ID getId();

}
