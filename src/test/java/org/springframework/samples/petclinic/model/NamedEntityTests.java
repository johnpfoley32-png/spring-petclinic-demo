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
package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.PetType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link NamedEntity}.
 * <p>
 * Uses {@link PetType} as a concrete subclass since {@code NamedEntity} is abstract.
 */
class NamedEntityTests {

	@Test
	void testToStringReturnsNameWhenSet() {
		PetType petType = new PetType();
		petType.setName("cat");

		assertThat(petType.toString()).isEqualTo("cat");
	}

	@Test
	void testToStringReturnsNullPlaceholderWhenNameIsNull() {
		PetType petType = new PetType();

		assertThat(petType.toString()).isEqualTo("<null>");
	}

}
