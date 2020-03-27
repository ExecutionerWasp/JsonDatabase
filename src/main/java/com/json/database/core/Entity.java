package com.json.database.core;

import java.io.Serializable;

/**
 * @author Alvin
 **/
@FunctionalInterface
public interface Entity<ID extends Serializable> {

    ID getId();

}
