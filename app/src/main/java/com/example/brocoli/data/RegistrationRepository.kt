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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.brocoli.data.local.database.Registration
import com.example.brocoli.data.local.database.RegistrationDao
import javax.inject.Inject

interface RegistrationRepository {
    val registrations: Flow<List<Registration>>

    suspend fun add(name: String, email: String)
    fun deleteAll()
}

class DefaultRegistrationRepository @Inject constructor(
    private val registrationDao: RegistrationDao
) : RegistrationRepository {

    override val registrations: Flow<List<Registration>> =
        registrationDao.getRegistrations()

    override suspend fun add(name: String, email: String) {
        registrationDao.insertRegistration(Registration(name = name, email = email))
    }

    override fun deleteAll() {
        registrationDao.deleteAll()
    }
}
