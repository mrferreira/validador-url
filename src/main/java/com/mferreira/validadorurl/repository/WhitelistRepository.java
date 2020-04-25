package com.mferreira.validadorurl.repository;

import com.mferreira.validadorurl.model.Whitelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhitelistRepository extends JpaRepository<Whitelist, Long> {
    Whitelist findByClient(String client);
}
