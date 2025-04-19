# 🍽️ Food Change Mood

Welcome to **Food Change Mood**, a fun and insightful Kotlin-based console application built during **Week 3 of The Chance Program**. This project is designed to help users explore a wide variety of meals and food-related features using a large dataset (`food.csv`) that includes nutrition, ingredients, tags, and more.

---

## 📦 Dataset Information

The dataset used in this project is `food.csv`, and it contains:

- Nutrition as an **array**: `[Calories, Total Fat, Sugar, Sodium, Protein, Saturated Fat, Carbohydrates]`
- Some meals with **null descriptions** (2%)
- Meal metadata such as name, ingredients, tags, preparation time, and number of steps

---

## 🛠️ Features

1. **Healthy Fast Food Filter**  
   - Lists meals prepared in ≤15 mins with very low total fat, saturated fat, and carbs.

2. **Advanced Meal Search by Name**  
   - Uses fast and typo-tolerant search algorithms (e.g., Knuth-Morris-Pratt).

3. **Identify Iraqi Meals**  
   - Finds meals tagged with "iraqi" or with "Iraq" in the description.

4. **Easy Food Suggestion Game**  
   - Recommends 10 easy meals (≤30 mins, ≤5 ingredients, ≤6 steps).

5. **Guess the Preparation Time Game**  
   - Users guess the prep time of a random meal (3 attempts allowed).

6. **Sweets with No Eggs**  
   - Recommends one egg-free sweet at a time; user can like/dislike to view or skip.

7. **Keto Diet Meal Helper**  
   - Suggests keto-friendly meals using nutrition filters (no repetition).

8. **Search by Add Date**  
   - Lists meals added on a specific date (with date format and not-found error handling).

9. **Gym Helper**  
   - Suggests meals based on user-defined calorie and protein goals.

10. **Explore International Food Culture**  
    - Enter a country name to explore up to 20 related meals randomly.

11. **Ingredient Game**  
    - Guess the correct ingredient out of 3 options; gain 1000 points per correct guess up to 15 rounds.

12. **I Love Potato**  
    - Randomly shows 10 meals that include potatoes in their ingredients.

13. **So Thin Problem**  
    - Recommends meals with more than 700 calories, one at a time.

14. **Seafood Ranking**  
    - Lists all seafood meals sorted by protein content, displaying rank, name, and protein amount.

15. **Group-Friendly Italian Meals**  
    - Returns all Italian meals tagged as suitable for large groups.

---

## ⚠️ Notes

- No unit tests are required this week — focus is on implementing all use cases and handling dataset complexities.
- Make sure to handle parsing of array-based columns properly.
- This is a **console-based application** for now.

---

## 📅 Upcoming

- **Unit Testing** for all features (starting next week)
- **Code optimization and documentation**

---

## 👨‍💻 Contributors

- Developed with 💝 by [@MoscowSquad](https://github.com/MoscowSquad)
