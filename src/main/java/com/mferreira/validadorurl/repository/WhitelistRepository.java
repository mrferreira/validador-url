package com.mferreira.validadorurl.repository;

import com.mferreira.validadorurl.model.Whitelist;
import org.springframework.data.repository.CrudRepository;

public interface WhitelistRepository extends CrudRepository<Whitelist, Long> {
    Whitelist findByClient(String client);
}
