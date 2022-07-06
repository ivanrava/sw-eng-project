package it.unibs.ing.tests.unit;

import it.unibs.ing.ingsw.exceptions.EmptyConfigException;
import it.unibs.ing.ingsw.io.batch.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserTest {

    JsonParser jsonParser;
    File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        jsonParser = new JsonParser();
        tempFile = File.createTempFile("config", ".json");
    }

    @Test
    void readConfigJsonEmptyFile() {
        assertThrows(EmptyConfigException.class, () -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    @Test
    void readConfigJsonWrongFormat() throws IOException {
        printToTempFile("{wrongformat:\"wrongformat\"}");
        assertThrows(EmptyConfigException.class, () -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    @Test
    void readConfigJsonSuccess() throws IOException {
        printCorrectConfigToTempFile();
        assertDoesNotThrow(() -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    @Test
    void readConfigJsonWithEmptyIntervals() throws IOException {
        printConfigWithTimeIntervalsEmptyConfigToTempFile();
        assertThrows(EmptyConfigException.class, () -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    @Test
    void readConfigJsonWithEmptyDays() throws IOException {
        printConfigWithEmptyDaysToTempFile();
        assertThrows(EmptyConfigException.class, () -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    @Test
    void readConfigJsonWithEmptyPlaces() throws IOException {
        printConfigWithEmptyPlacesToTempFile();
        assertThrows(EmptyConfigException.class, () -> jsonParser.readConfigJson(tempFile.getAbsolutePath()));
    }

    void printToTempFile(String content) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
        printWriter.print(content);
        printWriter.flush();
        printWriter.close();
    }

    void printCorrectConfigToTempFile() throws IOException {
        printToTempFile(
                """
                        {
                          "square": "Brescia",
                          "places": ["Portici", "UniBS - via Branze 38", "Fermata metro Vittoria"],
                          "deadline": 10,
                          "days": [6,7],
                          "timeIntervals": [
                            {
                              "start": "08:00",
                              "stop": "11:30"
                            },
                            {
                              "start": "14:00",
                              "stop": "18:30"
                            },
                            {
                              "start": "20:00",
                              "stop": "22:00"
                            }
                          ]
                        }"""
        );
    }

    void printConfigWithTimeIntervalsEmptyConfigToTempFile() throws IOException {
        printToTempFile(
                """
                        {
                          "square": "Brescia",
                          "places": ["Portici", "UniBS - via Branze 38", "Fermata metro Vittoria"],
                          "deadline": 10,
                          "days": [6,7],
                          "timeIntervals": []
                        }"""
        );
    }

    void printConfigWithEmptyDaysToTempFile() throws IOException {
        printToTempFile(
                """
                        {
                          "square": "Brescia",
                          "places": ["Portici", "UniBS - via Branze 38", "Fermata metro Vittoria"],
                          "deadline": 10,
                          "days": [],
                          "timeIntervals": [
                            {
                              "start": "08:00",
                              "stop": "11:30"
                            },
                            {
                              "start": "14:00",
                              "stop": "18:30"
                            },
                            {
                              "start": "20:00",
                              "stop": "22:00"
                            }
                          ]
                        }"""
        );
    }

    void printConfigWithEmptyPlacesToTempFile() throws IOException {
        printToTempFile(
                """
                        {
                          "square": "Brescia",
                          "places": [],
                          "deadline": 10,
                          "days": [6,7],
                          "timeIntervals": [
                            {
                              "start": "08:00",
                              "stop": "11:30"
                            },
                            {
                              "start": "14:00",
                              "stop": "18:30"
                            },
                            {
                              "start": "20:00",
                              "stop": "22:00"
                            }
                          ]
                        }"""
        );
    }
}