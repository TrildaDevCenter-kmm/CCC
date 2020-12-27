/*
 * Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.ccc.client.ui.currencies

import com.github.mustafaozhan.ccc.client.base.BaseViewModel
import com.github.mustafaozhan.ccc.client.util.MINIMUM_ACTIVE_CURRENCY
import com.github.mustafaozhan.ccc.client.util.isRewardExpired
import com.github.mustafaozhan.ccc.client.util.removeUnUsedCurrencies
import com.github.mustafaozhan.ccc.client.util.toUnit
import com.github.mustafaozhan.ccc.common.db.CurrencyDao
import com.github.mustafaozhan.ccc.common.log.kermit
import com.github.mustafaozhan.ccc.common.model.Currency
import com.github.mustafaozhan.ccc.common.model.CurrencyType
import com.github.mustafaozhan.ccc.common.settings.SettingsRepository
import com.github.mustafaozhan.scopemob.either
import com.github.mustafaozhan.scopemob.whether
import com.github.mustafaozhan.scopemob.whetherNot
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions")
class CurrenciesViewModel(
    private val settingsRepository: SettingsRepository,
    private val currencyDao: CurrencyDao
) : BaseViewModel(), CurrenciesEvent {

    // region SEED
    private val _state = MutableCurrenciesState()
    val state = CurrenciesState(_state)

    private val _effect = BroadcastChannel<CurrenciesEffect>(Channel.BUFFERED)
    val effect = _effect.asFlow()

    val data = CurrenciesData()

    fun getEvent() = this as CurrenciesEvent
    // endregion

    init {
        kermit.d { "CurrenciesViewModel init" }
        _state._loading.value = true

        clientScope.launch {
            currencyDao.collectAllCurrencies()
                .map { it.removeUnUsedCurrencies() }
                .collect { currencyList ->

                    _state._currencyList.value = currencyList
                    data.unFilteredList = currencyList

                    currencyList
                        .filter { it.isActive }.size
                        .whether { it < MINIMUM_ACTIVE_CURRENCY }
                        ?.whetherNot { settingsRepository.firstRun }
                        ?.let { _effect.send(FewCurrencyEffect) }

                    verifyCurrentBase()
                    filterList(data.query)
                    _state._selectionVisibility.value = false
                }
        }
        filterList("")
    }

    private fun verifyCurrentBase() = settingsRepository.currentBase.either(
        { equals(CurrencyType.NULL.toString()) },
        { base ->
            state.currencyList.value
                .filter { it.name == base }
                .toList().firstOrNull()?.isActive == false
        }
    )?.let {
        updateCurrentBase(
            state.currencyList.value
                .firstOrNull { it.isActive }?.name
                ?: CurrencyType.NULL.toString()
        )
    }

    private fun updateCurrentBase(newBase: String) = clientScope.launch {
        settingsRepository.currentBase = newBase
        _effect.send(ChangeBaseNavResultEffect(newBase))
    }.toUnit()

    fun hideSelectionVisibility() {
        _state._selectionVisibility.value = false
    }

    fun filterList(txt: String) = data.unFilteredList
        ?.filter { (name, longName, symbol) ->
            name.contains(txt, true) ||
                    longName.contains(txt, true) ||
                    symbol.contains(txt, true)
        }?.toMutableList()
        ?.let {
            _state._currencyList.value = it
            _state._loading.value = false
        }.run {
            data.query = txt
            true
        }

    fun isRewardExpired() = settingsRepository.adFreeActivatedDate.isRewardExpired()

    fun isFirstRun() = settingsRepository.firstRun

    override fun onCleared() {
        kermit.d { "CurrenciesViewModel onCleared" }
        super.onCleared()
    }

    // region Event
    override fun updateAllCurrenciesState(state: Boolean) = clientScope.launch {
        kermit.d { "CurrenciesViewModel updateAllCurrenciesState $state" }
        currencyDao.updateAllCurrencyState(state)
    }.toUnit()

    override fun onItemClick(currency: Currency) = clientScope.launch {
        kermit.d { "CurrenciesViewModel onItemClick ${currency.name}" }
        currencyDao.updateCurrencyStateByName(currency.name, !currency.isActive)
    }.toUnit()

    override fun onDoneClick() = clientScope.launch {
        kermit.d { "CurrenciesViewModel onDoneClick" }
        _state._currencyList.value
            .filter { it.isActive }.size
            .whether { it < MINIMUM_ACTIVE_CURRENCY }
            ?.let { _effect.send(FewCurrencyEffect) }
            ?: run {
                settingsRepository.firstRun = false
                _effect.send(CalculatorEffect)
            }
    }.toUnit()

    override fun onItemLongClick() = _state._selectionVisibility.value.let {
        kermit.d { "CurrenciesViewModel onItemLongClick" }
        _state._selectionVisibility.value = !it
        true
    }

    override fun onCloseClick() = clientScope.launch {
        kermit.d { "CurrenciesViewModel onCloseClick" }
        if (_state._selectionVisibility.value) {
            _state._selectionVisibility.value = false
        } else {
            _effect.send(BackEffect)
        }.run {
            filterList("")
        }
    }.toUnit()
    // endregion
}
