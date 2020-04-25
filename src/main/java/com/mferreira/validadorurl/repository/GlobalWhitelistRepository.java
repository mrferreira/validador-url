package com.mferreira.validadorurl.repository;

import com.mferreira.validadorurl.model.GlobalWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalWhitelistRepository extends JpaRepository<GlobalWhitelist, Long> {
}
