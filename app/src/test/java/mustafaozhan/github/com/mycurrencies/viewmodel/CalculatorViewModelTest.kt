/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package mustafaozhan.github.com.mycurrencies.viewmodel

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ObsoleteCoroutinesApi
import mustafaozhan.github.com.mycurrencies.data.api.ApiRepository
import mustafaozhan.github.com.mycurrencies.data.db.CurrencyDao
import mustafaozhan.github.com.mycurrencies.data.db.OfflineRatesDao
import mustafaozhan.github.com.mycurrencies.data.preferences.PreferencesRepository
import mustafaozhan.github.com.mycurrencies.model.Currency
import mustafaozhan.github.com.mycurrencies.ui.main.calculator.CalculatorData.Companion.KEY_AC
import mustafaozhan.github.com.mycurrencies.ui.main.calculator.CalculatorData.Companion.KEY_DEL
import mustafaozhan.github.com.mycurrencies.ui.main.calculator.CalculatorViewModel
import mustafaozhan.github.com.mycurrencies.ui.main.calculator.OpenBarEffect
import mustafaozhan.github.com.mycurrencies.ui.main.calculator.ShowRateEffect
import mustafaozhan.github.com.mycurrencies.util.extension.getCurrencyConversionByRate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ObsoleteCoroutinesApi
@RunWith(JUnit4::class)
class CalculatorViewModelTest : BaseViewModelTest<CalculatorViewModel>() {

    override lateinit var viewModel: CalculatorViewModel

    @RelaxedMockK
    lateinit var preferencesRepository: PreferencesRepository

    @MockK
    lateinit var apiRepository: ApiRepository

    @RelaxedMockK
    lateinit var currencyDao: CurrencyDao

    @MockK
    lateinit var offlineRatesDao: OfflineRatesDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CalculatorViewModel(
            preferencesRepository,
            apiRepository,
            currencyDao,
            offlineRatesDao
        )
    }

    @Test
    fun `spinner item click`() = with(viewModel) {
        val clickedItem = "asd"
        getEvent().onSpinnerItemSelected(clickedItem)
        assertEquals(clickedItem, state.base.value)
    }

    @Test
    fun `bar click`() = with(viewModel) {
        getEvent().onBarClick()
        assertEquals(OpenBarEffect, effect.value)
    }

    @Test
    fun `on item click`() = with(viewModel) {
        val currency = Currency("USD", "Dollar", "$", 0.0, true)
        val conversion = "123.456"
        getEvent().onItemClick(currency, conversion)

        assertEquals(currency.name, state.base.value)
        assertEquals(conversion, state.input.value)

        val unValidConversion = "123."
        val validConversion = "123"
        getEvent().onItemClick(currency, unValidConversion)
        assertEquals(validConversion, state.input.value)
    }

    @Test
    fun `on item long click`() = with(viewModel) {
        val currency = Currency("USD", "Dollar", "$", 0.0, true)

        getEvent().onItemLongClick(currency)

        assertEquals(
            ShowRateEffect(
                currency.getCurrencyConversionByRate(data.currentBase, data.rates),
                currency.name
            ),
            effect.value
        )
    }

    @Test
    fun `on key press`() = with(viewModel) {
        val oldValue = state.input.value
        val key = "1"
        getEvent().onKeyPress(key)
        assertEquals(oldValue + key, state.input.value)

        getEvent().onKeyPress(KEY_AC)
        assertEquals("", state.input.value)

        val currentInput = "12345"
        getEvent().onKeyPress(currentInput)
        getEvent().onKeyPress(KEY_DEL)
        assertEquals(currentInput.dropLast(1), state.input.value)
    }
}
