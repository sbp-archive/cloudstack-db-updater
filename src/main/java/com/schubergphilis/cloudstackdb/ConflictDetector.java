package com.schubergphilis.cloudstackdb;

import java.util.List;

public interface ConflictDetector {

    public List<Conflict> detect();

}
