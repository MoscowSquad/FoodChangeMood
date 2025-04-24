package logic.usecases



import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.SearchMealByCountryUseCase
import org.example.model.Exceptions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows



class SearchMealByCountryUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var searchMealByCountryUseCase: SearchMealByCountryUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk(relaxed = true)
        searchMealByCountryUseCase = SearchMealByCountryUseCase(mealRepository)
    }

    @Test
    fun `should return one meal matching with country`() {
        // Given
        val trueMeal = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "mexican"
            ),
            description = "autumn is my favorite time of year to cook! this recipe can be prepared either spicy or sweet, your choice! two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
        )

        val otherMeal1 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients."
        )
        val otherMeal2 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "weeknight", "time-to-make", "course", "main-ingredient",
            ),
            description = "easy one-pot dinner."
        )
        every { mealRepository.getAllMeals() } returns listOf(trueMeal , otherMeal1 , otherMeal2)


        val searchInput = "mexican"

        // When
        val result = searchMealByCountryUseCase.searchMealsByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal)

    }
    @Test
    fun `should throw exception when no meal found`() {
        // Given
        val meal1 = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient"
            ),
            description = "autumn is my favorite time of year to cook!",
        )

        val meal2 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients."
        )
        val meal3 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "weeknight", "time-to-make", "course"
            ),
            description = "easy one-pot dinner."
        )
        every { mealRepository.getAllMeals() } returns listOf(meal1 , meal2 , meal3)


        val searchInput = "Egypt"

        // When


        // When & Then
        assertThrows<Exceptions.NoMealsFoundException>{
            searchMealByCountryUseCase.searchMealsByCountry(searchInput)
        }
    }

    @Test
    fun `should return meals matching with country in name`() {
        // Given
        val trueMeal = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "autumn is my favorite time of year to cook!",
        )

        val otherMeal1 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients. "
        )
        val otherMeal2 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "weeknight", "time-to-make", "course", "main-ingredient", "cuisine",
            ),
            description = "easy one-pot dinner."
        )
        every { mealRepository.getAllMeals() } returns listOf(trueMeal , otherMeal1 , otherMeal2)


        val searchInput = "mexican"

        // When
        val result = searchMealByCountryUseCase.searchMealsByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal)

    }
    @Test
    fun `should return meals matching with country in description`() {
        // Given
        val trueMeal = createMeal(
            name = "arriba   baked winter squash  style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "autumn is my favorite time of year to cook! mexican",
        )

        val otherMeal1 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers."
        )
        val otherMeal2 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "weeknight", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "occasion"
            ),
            description = "easy one-pot dinner."
        )
        every { mealRepository.getAllMeals() } returns listOf(trueMeal , otherMeal1 , otherMeal2)


        val searchInput = "mexican"

        // When
        val result = searchMealByCountryUseCase.searchMealsByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal)
    }
    @Test
    fun `should return meals matching with country in tags`() {
        // Given
        val trueMeal = createMeal(
            name = "arriba   baked winter squash style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "mexican", "main-ingredient", "cuisine"
            ),
            description = "autumn is my favorite time of year to cook! this recipe can be prepared either spicy or sweet, your choice! two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
        )

        val otherMeal1 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients."
        )
        val otherMeal2 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "weeknight",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "north-american",
                "main-dish"
            ),
            description = "easy one-pot dinner."
        )
        every { mealRepository.getAllMeals() } returns listOf(trueMeal , otherMeal1 , otherMeal2)


        val searchInput = "mexican"

        // When
        val result = searchMealByCountryUseCase.searchMealsByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal)

    }
    @Test
    fun `should return more than one meal matching with country`(){
        val trueMeal = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "autumn is my favorite time of year to cook! spic!"
        )

        val trueMeal2 = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be mexican prebaked a bit before adding ingredients."
        )
        val trueMeal3 = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "mexican", "weeknight", "time-to-make", "course", "main-ingredient"
            ),
            description = "easy one-pot dinner."
        )
        // Given
        every { mealRepository.getAllMeals() } returns listOf(trueMeal , trueMeal2 , trueMeal3)


        val searchInput = "mexican"

        // When
        val result = searchMealByCountryUseCase.searchMealsByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal , trueMeal2 , trueMeal3)
    }
    @Test
    fun `should throw exception when no meal found when return empty`(){
        every { mealRepository.getAllMeals() } returns listOf()
        val searchInput = "Egypt"

        assertThrows<Exceptions.NoMealsFoundException>{
            searchMealByCountryUseCase.searchMealsByCountry(searchInput)
        }
    }
}