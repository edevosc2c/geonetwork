/*
 * Copyright (C) 2021 by the geOrchestra PSC
 *
 * This file is part of geOrchestra.
 *
 * geOrchestra is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * geOrchestra is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * geOrchestra.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.geonetwork.security.external.repository.jpa;

import java.util.Optional;

import org.fao.geonet.domain.Group;
import org.fao.geonet.domain.external.ExternalGroupLink;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository to track 1:1 relationships between externally defined groups
 * (given by their id), and GeoNetwork {@link Group groups}.
 */
@Repository
public interface ExternalGroupLinkRepository extends PagingAndSortingRepository<ExternalGroupLink, String> {
    
    Optional<ExternalGroupLink> findByName(String name);
}
