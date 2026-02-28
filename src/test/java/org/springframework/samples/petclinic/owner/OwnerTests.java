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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link Owner}.
 * <p>
 * Covers branch paths in getPet(Integer), getPet(String, boolean), addPet(Pet), and
 * addVisit(Integer, Visit) that were previously untested.
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setup() {
		owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}

	@Test
	void testGetPetByIdReturnsMatchingPet() {
		Pet pet = new Pet();
		pet.setName("Leo");
		owner.addPet(pet);
		pet.setId(7);

		assertThat(owner.getPet(7)).isSameAs(pet);
	}

	@Test
	void testGetPetByIdReturnsNullWhenNoMatch() {
		Pet pet = new Pet();
		pet.setName("Leo");
		owner.addPet(pet);
		pet.setId(7);

		assertThat(owner.getPet(999)).isNull();
	}

	@Test
	void testGetPetByIdSkipsNewPets() {
		// A "new" pet has id == null, so getPet(Integer) should skip it
		Pet newPet = new Pet();
		newPet.setName("Unsaved");
		owner.addPet(newPet);

		assertThat(owner.getPet(1)).isNull();
	}

	@Test
	void testGetPetByNameReturnsPet() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		assertThat(owner.getPet("Buddy")).isSameAs(pet);
	}

	@Test
	void testGetPetByNameIsCaseInsensitive() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		assertThat(owner.getPet("buddy")).isSameAs(pet);
	}

	@Test
	void testGetPetByNameReturnsNullWhenNoMatch() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);

		assertThat(owner.getPet("Unknown")).isNull();
	}

	@Test
	void testGetPetByNameIgnoreNewTrue() {
		// New pet (no id) should be ignored when ignoreNew=true
		Pet newPet = new Pet();
		newPet.setName("Buddy");
		owner.addPet(newPet);

		assertThat(owner.getPet("Buddy", true)).isNull();
	}

	@Test
	void testGetPetByNameIgnoreNewFalse() {
		// New pet (no id) should be returned when ignoreNew=false
		Pet newPet = new Pet();
		newPet.setName("Buddy");
		owner.addPet(newPet);

		assertThat(owner.getPet("Buddy", false)).isSameAs(newPet);
	}

	@Test
	void testGetPetByNameIgnoreNewTrueReturnsSavedPet() {
		// Saved pet (has id) should be returned even when ignoreNew=true
		Pet savedPet = new Pet();
		savedPet.setName("Buddy");
		owner.addPet(savedPet);
		savedPet.setId(5);

		assertThat(owner.getPet("Buddy", true)).isSameAs(savedPet);
	}

	@Test
	void testGetPetByNameSkipsPetsWithNullName() {
		Pet pet = new Pet();
		// name is null by default
		owner.addPet(pet);

		assertThat(owner.getPet("Anything")).isNull();
	}

	@Test
	void testAddPetIgnoresNonNewPet() {
		Pet pet = new Pet();
		pet.setName("Leo");
		pet.setId(10); // Not new — already persisted

		owner.addPet(pet);

		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddPetAddsNewPet() {
		Pet pet = new Pet();
		pet.setName("Leo");
		// id is null — this is a new pet

		owner.addPet(pet);

		assertThat(owner.getPets()).hasSize(1);
		assertThat(owner.getPets().get(0)).isSameAs(pet);
	}

	@Test
	void testAddVisitDelegatesToPet() {
		Pet pet = new Pet();
		pet.setName("Leo");
		owner.addPet(pet);
		pet.setId(7);

		Visit visit = new Visit();
		visit.setDescription("Checkup");

		owner.addVisit(7, visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next().getDescription()).isEqualTo("Checkup");
	}

	@Test
	void testAddVisitThrowsForNullPetId() {
		assertThatThrownBy(() -> owner.addVisit(null, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitThrowsForNullVisit() {
		assertThatThrownBy(() -> owner.addVisit(1, null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitThrowsForInvalidPetId() {
		assertThatThrownBy(() -> owner.addVisit(999, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testToStringContainsOwnerFields() {
		owner.setId(1);
		String result = owner.toString();

		assertThat(result).contains("Franklin");
		assertThat(result).contains("George");
		assertThat(result).contains("Madison");
	}

}
