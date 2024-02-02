    package edu.iu.c322.test1.repository;

    import edu.iu.c322.test1.model.Question;
    import org.junit.jupiter.api.*;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;

    class FileRepositoryTest {

        private static final String TEST_FILE_PATH = "questions-test.txt";

        @BeforeAll
        static void setup() throws IOException {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        }

        @AfterAll
        static void cleanup() throws IOException {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        }

        @Test
        void addQuestion1() {
            Question q = new Question(1,
                    "Which word matches the image?",
                    "elephant",
                    new String[]{"tiger", "bear", "elephant"});

            addToDB(q);
        }

        @Test
        void addQuestion2() {
            Question q = new Question(2,
                    "Which word matches the image?",
                    "leopard",
                    new String[]{"tiger", "lion", "leopard"});

            addToDB(q);
        }

        @Test
        void addQuestion3() {
            Question q = new Question(3,
                    "Which word matches the image?",
                    "lion",
                    new String[]{"cheetah", "lion", "leopard"});

            addToDB(q);
        }

        void addToDB(Question question) {
            FileRepository fileRepository = new FileRepository();
            boolean result = false;
            try {
                result = fileRepository.add(question);
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            }
            assertTrue(result);
        }

        @Test
        void findAll() {
            FileRepository fileRepository = new FileRepository();
            try {
                List<Question> data = fileRepository.findAll();
                assertEquals(3, data.size());
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            }
        }
    }
