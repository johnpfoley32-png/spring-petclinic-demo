/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Pet}.
 */
class PetTests {

	@Test
	void testSetAndGetBirthDate() {
		Pet pet = new Pet();
		LocalDate date = LocalDate.of(2020, 6, 15);
		pet.setBirthDate(date);

		assertThat(pet.getBirthDate()).isEqualTo(date);
	}

	@Test
	void testSetAndGetType() {
		Pet pet = new Pet();
		PetType type = new PetType();
		type.setName("cat");
		pet.setType(type);

		assertThat(pet.getType()).isSameAs(type);
	}

	@Test
	void testGetVisitsIsInitiallyEmpty() {
		Pet pet = new Pet();

		assertThat(pet.getVisits()).isEmpty();
	}

	@Test
	void testAddVisit() {
		Pet pet = new Pet();
		Visit visit = new Visit();
		visit.setDescription("Annual checkup");

		pet.addVisit(visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits()).contains(visit);
	}

	@Test
	void testGetVisitsGrowsAsVisitsAreAdded() {
		Pet pet = new Pet();

		assertThat(pet.getVisits()).isEmpty();

		Visit visit1 = new Visit();
		visit1.setDescription("First visit");
		pet.addVisit(visit1);
		assertThat(pet.getVisits()).hasSize(1);

		Visit visit2 = new Visit();
		visit2.setDescription("Second visit");
		pet.addVisit(visit2);
		assertThat(pet.getVisits()).hasSize(2);
	}

}
