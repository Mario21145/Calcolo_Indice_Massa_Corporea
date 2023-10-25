package com.example.bodymassindex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import com.example.bodymassindex.viewModel.BmiViewModel
import org.junit.Rule

class ExampleUnitTest {

    private lateinit var viewModel : BmiViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        viewModel = BmiViewModel()
    }

    @Test
    fun setWeight(){
        viewModel.setWeight("90")
        assertEquals(viewModel.weight.value , 90.0)
    }

    @Test
    fun setHeight(){
        viewModel.setHeight("190")
        assertEquals(viewModel.height.value , 190.0)
    }

    @Test
    fun setSex(){
        viewModel.setSex("men")
        assertEquals(viewModel.sex.value , "men")
    }

    @Test
    fun testCalcBmi(){
        viewModel.setWeight("90")
        viewModel.setHeight("188")
        viewModel.calcBmi()

        assertEquals(viewModel.liveBmi.value , 25.5)
        assertNotNull(viewModel.liveBmi.value)
    }

    @Test
    fun testResetCalcBmi(){
        viewModel.setWeight("90")
        viewModel.setHeight("188")
        viewModel.calcBmi()
        viewModel.resetCalcBmi()

        assertEquals(viewModel.liveBmi.value , 0.0)
    }

    @Test
    fun testResetAll(){
        viewModel.setWeight("90")
        viewModel.setHeight("188")
        viewModel.setSex("uomo")
        viewModel.resetAll()

        assertEquals(viewModel.weight.value , 0.0)
        assertEquals(viewModel.height.value , 0.0)
        assertEquals(viewModel.sex.value , "")
    }
}