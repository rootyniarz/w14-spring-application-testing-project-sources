package pl.zajavka.mortgage.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.ResourceUtils;
import pl.zajavka.mortgage.configuration.CalculatorConfiguration;
import pl.zajavka.mortgage.services.MortgageCalculationService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringJUnitConfig(classes = {CalculatorConfiguration.class})
public class MortgageCalculationServiceIT {
    private static final Path RESULT_FILE_PATH = Paths.get("build/generated/calculationResult.txt");
    private static final String EXPECTED_GENERATED_CONTENT = "classpath:expectedGeneratedResult.txt";

    @Autowired
    private MortgageCalculationService mortgageCalculationService;

    @BeforeEach
    public void setUp() {
        Assertions.assertNotNull(mortgageCalculationService);
    }

    @Test
    @DisplayName("That whole app calculation works correctly")
    void test() {
        //given, when
        mortgageCalculationService.calculate();

        //then
        final var generatedResultContent = readGeneratedResultContent();
        final var expectedGeneratedResultContent = readExpectedGeneratedResultContent();

        for (int i = 0; i < expectedGeneratedResultContent.size(); i++) {
            Assertions.assertEquals(expectedGeneratedResultContent.get(i), generatedResultContent.get(i));
        }

    }

    private List<String> readGeneratedResultContent() {
        try {
        return Files.readAllLines(RESULT_FILE_PATH);
        } catch (Exception e) {
            Assertions.fail("Reading file fails",e);
        }
        return List.of();
    }

    private List<String> readExpectedGeneratedResultContent() {
        try {
            File file = ResourceUtils.getFile(EXPECTED_GENERATED_CONTENT);
            return Files.readAllLines(file.toPath());
        } catch (Exception e) {
            Assertions.fail("Reading file fails",e);
        }
        return List.of();
    }


}

// 11;23