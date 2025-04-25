package utils

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.utils.CsvFileParser
import org.example.utils.CsvParser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CsvParserTest {
    private lateinit var csvFileParser: CsvFileParser
    private lateinit var csvParser: CsvParser

    @BeforeEach
    fun setUp() {
        csvFileParser = mockk(relaxed = true)
        csvParser = CsvParser(csvFileParser)
    }

    @Test
    fun `should parse the csv lines into meal when the csv lines are correct to parse`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,1,15,101,2023-06-01,\"['easy','quick']\",\"[250.0,8.0,3.0,500.0,7.0,2.0,30.0]\",3,\"['Boil','Mix','Serve']\",\"Simple pasta dish\",\"['pasta','oil','salt']\",3\n",
            "Salad,2,10,102,2023-06-02,\"['fresh','healthy']\",\"[120.0,4.0,2.5,150.0,2.0,0.5,10.0]\",2,\"['Chop','Mix']\",\"Green salad with dressing\",\"['lettuce','tomato','dressing']\",3\n",
            "Soup,3,25,103,2023-06-03,\"['warm','light']\",\"[180.0,6.0,1.0,300.0,5.0,1.5,20.0]\",3,\"['Boil','Simmer','Serve']\",\"Light vegetable soup\",\"['carrot','onion','water']\",3\n",
            "Toast,4,5,104,2023-06-04,\"['fast','snack']\",\"[90.0,3.0,1.2,120.0,2.0,0.4,12.0]\",2,\"['Toast','Butter']\",\"Quick buttered toast\",\"['bread','butter']\",2\n",
            "Omelette,5,7,105,2023-06-05,\"['protein','breakfast']\",\"[200.0,10.0,0.8,180.0,9.0,3.0,1.0]\",3,\"['Beat','Cook','Fold']\",\"Simple egg omelette\",\"['egg','salt','pepper']\",3\n",
            "Smoothie,6,6,106,2023-06-06,\"['cold','fruit']\",\"[160.0,2.0,14.0,80.0,3.0,0.1,22.0]\",2,\"['Blend','Pour']\",\"Fruit smoothie drink\",\"['banana','milk','berries']\",3"
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = 1, minutes = 15, contributorId = 101, submitted = "2023-06-01",
                    tags = listOf("easy", "quick"),
                    nutrition = Nutrition(250.0, 8.0, 3.0, 500.0, 7.0, 2.0, 30.0),
                    description = "Simple pasta dish",
                    nSteps = 3, steps = listOf("Boil", "Mix", "Serve"),
                    ingredients = listOf("pasta", "oil", "salt"), nIngredients = 3
                ),
                Meal(
                    name = "Salad", id = 2, minutes = 10, contributorId = 102, submitted = "2023-06-02",
                    tags = listOf("fresh", "healthy"),
                    nutrition = Nutrition(120.0, 4.0, 2.5, 150.0, 2.0, 0.5, 10.0),
                    nSteps = 2,
                    steps = listOf("Chop", "Mix"),
                    description = "Green salad with dressing",
                    ingredients = listOf("lettuce", "tomato", "dressing"),
                    nIngredients = 3
                ),
                Meal(
                    name = "Soup", id = 3, minutes = 25, contributorId = 103, submitted = "2023-06-03",
                    tags = listOf("warm", "light"),
                    nutrition = Nutrition(180.0, 6.0, 1.0, 300.0, 5.0, 1.5, 20.0),
                    nSteps = 3, steps = listOf("Boil", "Simmer", "Serve"),
                    description = "Light vegetable soup",
                    ingredients = listOf("carrot", "onion", "water"), nIngredients = 3
                ),
                Meal(
                    name = "Toast", id = 4, minutes = 5, contributorId = 104, submitted = "2023-06-04",
                    tags = listOf("fast", "snack"),
                    nutrition = Nutrition(90.0, 3.0, 1.2, 120.0, 2.0, 0.4, 12.0),
                    nSteps = 2,
                    steps = listOf("Toast", "Butter"),
                    description = "Quick buttered toast",
                    ingredients = listOf("bread", "butter"),
                    nIngredients = 2
                ),
                Meal(
                    name = "Omelette", id = 5, minutes = 7, contributorId = 105, submitted = "2023-06-05",
                    tags = listOf("protein", "breakfast"),
                    nutrition = Nutrition(200.0, 10.0, 0.8, 180.0, 9.0, 3.0, 1.0),
                    nSteps = 3,
                    steps = listOf("Beat", "Cook", "Fold"),
                    description = "Simple egg omelette",
                    ingredients = listOf("egg", "salt", "pepper"),
                    nIngredients = 3
                ),
                Meal(
                    name = "Smoothie", id = 6, minutes = 6, contributorId = 106, submitted = "2023-06-06",
                    tags = listOf("cold", "fruit"),
                    nutrition = Nutrition(160.0, 2.0, 14.0, 80.0, 3.0, 0.1, 22.0),
                    nSteps = 2, steps = listOf("Blend", "Pour"),
                    description = "Fruit smoothie drink",
                    ingredients = listOf("banana", "milk", "berries"), nIngredients = 3
                )
            )
        )
    }

    @Test
    fun `should parse the csv lines into meal when the csv lines are correct to parse even with null values`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            ",,,,,,,,,,,\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = null, id = null, minutes = null, contributorId = null, submitted = null,
                    tags = null, nutrition = null, description = null, nSteps = null, steps = null,
                    ingredients = null, nIngredients = null
                ),
            )
        )
    }

    @Test
    fun `should parse the csv lines into meal when the csv lines are correct to parse even with nutrition null values`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,1,15,101,2023-06-01,\"['easy','quick']\",\"[none,8.0,none,500.0,7.0,2.0,none]\",3,\"['Boil','Mix','Serve']\",\"Simple pasta dish\",\"['pasta','oil','salt']\",3\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = 1, minutes = 15, contributorId = 101, submitted = "2023-06-01",
                    tags = listOf("easy", "quick"),
                    nutrition = Nutrition(null, 8.0, null, 500.0, 7.0, 2.0, null),
                    description = "Simple pasta dish",
                    nSteps = 3, steps = listOf("Boil", "Mix", "Serve"),
                    ingredients = listOf("pasta", "oil", "salt"), nIngredients = 3
                ),
            )
        )
    }

    @Test
    fun `should parse the csv lines into meal when the csv lines are correct to parse even with empty values`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,1,15,101,2023-06-01,\"['','']\",\"[250.0,8.0,3.0,500.0,7.0,2.0,30.0]\",3,\"['','','']\",\"\",\"['','','']\",3\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = 1, minutes = 15, contributorId = 101, submitted = "2023-06-01",
                    tags = listOf("", ""),
                    nutrition = Nutrition(250.0, 8.0, 3.0, 500.0, 7.0, 2.0, 30.0),
                    description = "",
                    nSteps = 3, steps = listOf("", "", ""),
                    ingredients = listOf("", "", ""), nIngredients = 3
                ),
            )
        )
    }

    @Test
    fun `should parse the csv lines into meal when the csv lines are correct to parse with parse incorrect values as null`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,s,@,^,2023-06-01,\"['easy','quick']\",\"[A,B,3.0,500.0,7.0,2.0,30.0]\",V,\"['Boil','Mix','Serve']\",\"Simple pasta dish\",\"['pasta','oil','salt']\",@\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = null, minutes = null, contributorId = null, submitted = "2023-06-01",
                    tags = listOf("easy", "quick"),
                    nutrition = Nutrition(null, null, 3.0, 500.0, 7.0, 2.0, 30.0),
                    description = "Simple pasta dish",
                    nSteps = null, steps = listOf("Boil", "Mix", "Serve"),
                    ingredients = listOf("pasta", "oil", "salt"), nIngredients = null
                ),
            )
        )
    }


    @Test
    fun `should parse the csv lines into null when the csv lines are incorrect to parse`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Hello wold\n",
            ",s,@,^,2\"Simple pasta dish\",\"['pasta','oil','salt']\",@\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(emptyList<Meal>())
    }

    @Test
    fun `should return empty list when the data is empty lines`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "",
            "",
            "",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf<Meal>()
        )
    }

    @Test
    fun `should return empty list when the data is empty liness`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,1,15,101,2023-06-01,\"[]\",\"[]\",3,\"[]\",\"Simple pasta dish\",\"[]\",3\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = 1, minutes = 15, contributorId = 101, submitted = "2023-06-01",
                    tags = null,
                    nutrition = null,
                    description = "Simple pasta dish",
                    nSteps = 3, steps = null,
                    ingredients = null, nIngredients = 3
                ),
            )
        )
    }

    @Test
    fun `xx`() {
        every { csvFileParser.getLines() } returns listOf(
            "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients",
            "Pasta,1,15,101,2023-06-01,\"['easy','quick']\",\"[250.0,8.0,3.0,500.0,7.0,2.0,30.0]\",3,\"['Boil','Mix','Serve']\",\"Simple pasta dish\",\"['pasta','oil','salt']\",3\n",
        )

        val meals = csvParser.parseMealsCsv()
        Truth.assertThat(meals).isEqualTo(
            listOf(
                Meal(
                    name = "Pasta", id = 1, minutes = 15, contributorId = 101, submitted = "2023-06-01",
                    tags = listOf("easy", "quick"),
                    nutrition = Nutrition(250.0, 8.0, 3.0, 500.0, 7.0, 2.0, 30.0),
                    description = "Simple pasta dish",
                    nSteps = 3, steps = listOf("Boil", "Mix", "Serve"),
                    ingredients = listOf("pasta", "oil", "salt"), nIngredients = 3
                ),
            )
        )
    }

    @Test
    fun `should verify that getting the csv file lines via csvFileParser when getting the meals data`() {
        csvParser.parseMealsCsv()

        verify(exactly = 1) {
            csvFileParser.getLines()
        }
    }
}