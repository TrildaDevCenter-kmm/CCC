/*
 * Copyright (c) 2021 Mustafa Ozhan. All rights reserved.
 */

package com.oztechan.ccc.client.viewmodel

import com.oztechan.ccc.client.model.OldPurchase
import com.oztechan.ccc.client.model.RemoveAdType
import com.oztechan.ccc.client.util.after
import com.oztechan.ccc.client.util.before
import com.oztechan.ccc.client.util.calculateAdRewardEnd
import com.oztechan.ccc.client.viewmodel.adremove.AdRemoveEffect
import com.oztechan.ccc.client.viewmodel.adremove.AdRemoveState
import com.oztechan.ccc.client.viewmodel.adremove.AdRemoveViewModel
import com.oztechan.ccc.client.viewmodel.adremove.update
import com.oztechan.ccc.common.datasource.settings.SettingsDataSource
import com.oztechan.ccc.common.util.nowAsLong
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.given
import io.mockative.mock
import io.mockative.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AdRemoveViewModelTest : BaseViewModelTest() {

    @Mock
    private val settingsDataSource = mock(classOf<SettingsDataSource>())

    private val viewModel: AdRemoveViewModel by lazy {
        AdRemoveViewModel(settingsDataSource)
    }

    // SEED
    @Test
    fun check_data_is_null() {
        assertNull(viewModel.data)
    }

    @Test
    fun states_updates_correctly() {
        val adRemoveTypes = RemoveAdType.values().toList()
        val loading = Random.nextBoolean()
        val state = MutableStateFlow(AdRemoveState())

        state.update(
            adRemoveTypes = adRemoveTypes,
            loading = loading
        )

        state.value.let {
            assertEquals(loading, it.loading)
            assertEquals(adRemoveTypes, it.adRemoveTypes)
        }
    }

    // public methods
    @Test
    fun updateAddFreeDate() {
        viewModel.updateAddFreeDate(null)
        verify(settingsDataSource)
            .invocation { adFreeEndDate }
            .wasNotInvoked()

        RemoveAdType.values().forEach { adRemoveType ->
            viewModel.effect.before {
                viewModel.updateAddFreeDate(adRemoveType)
            }.after {
                assertEquals(AdRemoveEffect.AdsRemoved(adRemoveType, false), it)

                verify(settingsDataSource)
                    .invocation { adFreeEndDate = adRemoveType.calculateAdRewardEnd() }
                    .wasInvoked()
            }
        }
    }

    @Test
    fun restorePurchase() {
        given(settingsDataSource)
            .invocation { adFreeEndDate }
            .thenReturn(0)

        viewModel.effect.before {
            viewModel.restorePurchase(
                listOf(
                    OldPurchase(nowAsLong(), RemoveAdType.MONTH),
                    OldPurchase(nowAsLong(), RemoveAdType.YEAR)
                )
            )
        }.after {
            assertTrue { it is AdRemoveEffect.AdsRemoved }
            assertEquals(true, (it as? AdRemoveEffect.AdsRemoved)?.isRestorePurchase == true)
        }
    }

    @Test
    fun showLoadingView() {
        val mockValue = Random.nextBoolean()

        viewModel.showLoadingView(mockValue)

        assertEquals(mockValue, viewModel.state.value.loading)
    }

    @Test
    fun addPurchaseMethods() = RemoveAdType.values()
        .map { it.data }
        .forEach { removeAdData ->
            viewModel.state.before {
                viewModel.addPurchaseMethods(listOf(removeAdData))
            }.after {
                assertEquals(
                    true,
                    it?.adRemoveTypes?.contains(RemoveAdType.getById(removeAdData.id))
                )
            }
        }

    // Event
    @Test
    fun onAdRemoveItemClick() = with(viewModel) {
        effect.before {
            event.onAdRemoveItemClick(RemoveAdType.VIDEO)
        }.after {
            assertEquals(AdRemoveEffect.LaunchRemoveAdFlow(RemoveAdType.VIDEO), it)
        }

        effect.before {
            event.onAdRemoveItemClick(RemoveAdType.MONTH)
        }.after {
            assertEquals(AdRemoveEffect.LaunchRemoveAdFlow(RemoveAdType.MONTH), it)
        }

        effect.before {
            event.onAdRemoveItemClick(RemoveAdType.QUARTER)
        }.after {
            assertEquals(AdRemoveEffect.LaunchRemoveAdFlow(RemoveAdType.QUARTER), it)
        }

        effect.before {
            event.onAdRemoveItemClick(RemoveAdType.HALF_YEAR)
        }.after {
            assertEquals(AdRemoveEffect.LaunchRemoveAdFlow(RemoveAdType.HALF_YEAR), it)
        }

        effect.before {
            event.onAdRemoveItemClick(RemoveAdType.YEAR)
        }.after {
            assertEquals(AdRemoveEffect.LaunchRemoveAdFlow(RemoveAdType.YEAR), it)
        }
    }
}
