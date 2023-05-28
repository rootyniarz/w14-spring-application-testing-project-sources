package pl.zajavka.mortgage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zajavka.mortgage.fixtures.TestDataFixtures;
import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.Installment;
import pl.zajavka.mortgage.model.InstallmentAmounts;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ConstantAmountsCalculationServiceImplTest {

    @InjectMocks
    private ConstantAmountsCalculationServiceImpl constantAmountsCalculationService;

    @Test
    @DisplayName("Calculate installment amounts for first installment")
    void shouldCalculateFirstInstallmentCorrectly() {
        //given
        InputData inputData = TestDataFixtures.someInputData();
        InstallmentAmounts expected = TestDataFixtures.someInstallmentAmounts();



        //when
        InstallmentAmounts result = constantAmountsCalculationService.calculate(inputData, null);

        //then
        Assertions.assertEquals(expected, result);

    }

    @Test
    @DisplayName("Calculate installment amounts for other installment")
    void shouldCalculateOtherInstallmentCorrectly() {
        //given
        InputData inputData = TestDataFixtures.someInputData();
        Installment installment = TestDataFixtures.someInstallment();
        InstallmentAmounts expected = TestDataFixtures.someInstallmentAmounts()
                .withInstallmentAmount(new BigDecimal("3303.45"))
                .withInterestAmount(new BigDecimal("2483.87"))
                .withCapitalAmount(new BigDecimal("819.58"));

        //when
        InstallmentAmounts result = constantAmountsCalculationService.calculate(inputData, null, installment);


        //then
        Assertions.assertEquals(expected, result);
    }


}