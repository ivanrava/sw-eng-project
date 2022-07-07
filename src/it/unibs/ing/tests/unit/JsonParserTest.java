package it.unibs.ing.tests.unit;

import it.unibs.ing.ingsw.services.batch.exceptions.EmptyConfigException;
import it.unibs.ing.ingsw.services.batch.JsonParser;
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
    void readCategoryJson() throws IOException {
        printCorrectCategoryJsonToTempFile();
        assertDoesNotThrow(() -> jsonParser.readCategoriesJson(tempFile.getAbsolutePath()));
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

    void printCorrectCategoryJsonToTempFile() throws IOException {
        printToTempFile("""
                [
                  {
                    "name": "Libro",
                    "description": "Opera cartacea",
                    "fields": [
                      {
                        "name": "Titolo",
                        "required": true
                      },
                      {
                        "name": "Anno di uscita",
                        "required": true
                      },
                      {
                        "name": "Edizione",
                        "required": false
                      },
                      {
                        "name": "Autore",
                        "required": true
                      }
                    ],
                    "children": [
                      {
                        "name": "Romanzo",
                        "description": "Narrazione scritta in prosa",
                        "fields": [],
                        "children": [
                          {
                            "name": "Romanzo straniero",
                            "description": "Romanzo in lingua non italiana",
                            "fields": [
                              {
                                "name": "Lingua originale",
                                "required": true
                              }
                            ],
                            "children": []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    "name": "Videogioco",
                    "description": "Opera interattiva",
                    "fields": [
                      {
                        "name": "Titolo",
                        "required": true
                      },
                      {
                        "name": "Anno",
                        "required": true
                      },
                      {
                        "name": "Sviluppatore",
                        "required": false
                      },
                      {
                        "name": "Piattaforma",
                        "required": true
                      }
                    ],
                    "children": [
                      {
                        "name": "RPG",
                        "description": "Gioco di ruolo (Role-Play Game)",
                        "fields": [
                          {
                            "name": "Sinossi",
                            "required": false
                          }
                        ],
                        "children": [
                          {
                            "name": "ARPG",
                            "description": "Gioco di ruolo d'azione (Action Role-Play Game)",
                            "fields": [],
                            "children": []
                          },
                          {
                            "name": "JRPG",
                            "description": "Gioco di ruolo alla giapponese (Japanese Role-Play Game)",
                            "fields": [
                              {
                                "name": "Compositore",
                                "required": true
                              }
                            ],
                            "children": []
                          }
                        ]
                      },
                      {
                        "name": "Shooter",
                        "description": "Sparatutto",
                        "fields": [
                          {
                            "name": "Prospettiva",
                            "required": true
                          }
                        ],
                        "children": []
                      },
                      {
                        "name": "Metroidvania",
                        "description": "Gioco fondato su backtracking e esplorazione incrementale",
                        "fields": [],
                        "children": [
                          {
                            "name": "Soulslike",
                            "description": "Metroidvania con gameplay ispirato a Dark Souls",
                            "fields": [],
                            "children": []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    "name": "Film",
                    "description": "Opera visiva",
                    "fields": [
                      {
                        "name": "Titolo",
                        "required": true
                      },
                      {
                        "name": "Regista",
                        "required": true
                      },
                      {
                        "name": "Sceneggiatore",
                        "required": true
                      },
                      {
                        "name": "Anno",
                        "required": true
                      },
                      {
                        "name": "Genere",
                        "required": true
                      }
                    ],
                    "children": []
                  },
                  {
                    "name": "Macchina",
                    "description": "brum",
                    "fields": [
                      {
                      "name": "Titolo",
                      "required": true
                      }
                    ],
                    "children": [
                      {
                        "name": "sportiva",
                        "description": "rossa",
                        "fields": [
                          {
                            "name": "cilindrata",
                            "required": false
                          }
                        ],
                        "children": []
                      }
                    ]
                  },
                  {
                    "name": "Moto",
                    "description": "brumbrum",
                    "fields": [
                      {
                        "name": "Titolo",
                        "required": true
                      }
                    ],
                    "children": [
                      {
                        "name": "ducati",
                        "description": "rossa",
                        "fields": [
                          {
                            "name": "cilindrata",
                            "required": false
                          }
                        ],
                        "children": []
                      }
                    ]
                  }
                ]
                """);
    }
}