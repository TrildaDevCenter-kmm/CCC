/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package mustafaozhan.github.com.ui.main.settings

import androidx.lifecycle.viewModelScope
import com.github.mustafaozhan.basemob.model.MutableSingleLiveData
import com.github.mustafaozhan.basemob.model.SingleLiveData
import com.github.mustafaozhan.basemob.util.toUnit
import com.github.mustafaozhan.basemob.viewmodel.BaseViewModel
import com.github.mustafaozhan.scopemob.either
import com.github.mustafaozhan.scopemob.whether
import com.github.mustafaozhan.scopemob.whetherNot
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mustafaozhan.github.com.data.db.CurrencyDao
import mustafaozhan.github.com.data.model.Currencies
import mustafaozhan.github.com.data.model.Currency
import mustafaozhan.github.com.data.preferences.PreferencesRepository
import mustafaozhan.github.com.data.util.removeUnUsedCurrencies
import mustafaozhan.github.com.ui.main.MainData.Companion.MINIMUM_ACTIVE_CURRENCY
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val currencyDao: CurrencyDao
) : BaseViewModel(), SettingsEvent {

    // region SEED
    private val _states = SettingsStateBacking()
    val state = SettingsState(_states)

    private val _effect = MutableSingleLiveData<SettingsEffect>()
    val effect: SingleLiveData<SettingsEffect> = _effect

    val data = SettingsData(preferencesRepository)

    fun getEvent() = this as SettingsEvent
    // endregion

    init {
        _states._loading.value = true

        _states._searchQuery.addSource(state.searchQuery) {
            filterList(it)
        }

        viewModelScope.launch {
            currencyDao.getAllCurrencies()
                .map { it.removeUnUsedCurrencies() }
                .collect { currencyList ->

                    _states._currencyList.value = currencyList
                    data.unFilteredList = currencyList

                    currencyList
                        ?.filter { it.isActive }?.size
                        ?.whether { it < MINIMUM_ACTIVE_CURRENCY }
                        ?.whetherNot { data.firstRun }
                        ?.let { _effect.postValue(FewCurrencyEffect) }

                    verifyCurrentBase()
                    filterList("")
                }
        }
    }

    private fun filterList(txt: String) = data.unFilteredList
        ?.filter { (name, longName, symbol) ->
            name.contains(txt, true) ||
                longName.contains(txt, true) ||
                symbol.contains(txt, true)
        }?.toMutableList()
        ?.let {
            _states._currencyList.value = it
            _states._loading.value = false
        }

    private fun verifyCurrentBase() = data.currentBase.either(
        { equals(Currencies.NULL.toString()) },
        { base ->
            state.currencyList.value
                ?.filter { it.name == base }
                ?.toList()?.firstOrNull()?.isActive == false
        }
    )?.let {
        updateCurrentBase(
            state.currencyList.value
                ?.firstOrNull { it.isActive }?.name
                ?: Currencies.NULL.toString()
        )
    }.run {
        _states._searchQuery.value = ""
    }

    private fun updateCurrentBase(newBase: String) {
        data.currentBase = newBase
        _effect.postValue(ChangeBaseNavResultEffect(newBase))
    }

    // region Event
    override fun updateAllCurrenciesState(state: Boolean) = viewModelScope.launch {
        currencyDao.updateAllCurrencyState(state)
    }.toUnit()

    override fun onItemClick(currency: Currency) = viewModelScope.launch {
        currencyDao.updateCurrencyStateByName(currency.name, !currency.isActive)
    }.toUnit()

    override fun onDoneClick() = _states._currencyList.value
        ?.filter { it.isActive }?.size
        ?.whether { it < MINIMUM_ACTIVE_CURRENCY }
        ?.let { _effect.postValue(FewCurrencyEffect) }
        ?: run {
            data.firstRun = false
            _effect.postValue(CalculatorEffect)
        }
    // endregion
}
