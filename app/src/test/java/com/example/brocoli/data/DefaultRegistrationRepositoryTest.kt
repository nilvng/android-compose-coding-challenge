/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.brocoli.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.example.brocoli.data.local.database.Registration
import com.example.brocoli.data.local.database.RegistrationDao

/**
 * Unit tests for [DefaultRegistrationRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultRegistrationRepositoryTest {

    @Test
    fun registrations_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultRegistrationRepository(FakeRegistrationDao())

        repository.add("name", "email")

        assertEquals(repository.registrations.first().size, 1)
    }

}

private class FakeRegistrationDao : RegistrationDao {

    private val data = mutableListOf<Registration>()

    override fun getRegistrations(): Flow<List<Registration>> = flow {
        emit(data)
    }

    override suspend fun insertRegistration(item: Registration) {
        data.add(0, item)
    }

    override fun deleteAll() {
        data.clear()
    }
}
