package com.mferreira.validadorurl.repository;

import com.mferreira.validadorurl.model.GlobalWhitelist;
import org.springframework.data.repository.CrudRepository;

public interface GlobalWhitelistRepository extends CrudRepository<GlobalWhitelist, Long> {
}
