package vastraveda.features.feature6_quiz;

 update-datastore
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;
import java.util.ArrayList;

/**
 * Feature 6 — Clothing Quiz Service
 * Implementation of business logic to match users with traditional attire.
 */
public class Feature6Service {

    /**
     * Recommends clothing based on user preferences.
     * @param preferredGender The gender category (e.g., "Female", "Male")
     * @param preferredOccasion The event type (e.g., "Wedding", "Festival")
     * @return A list of matching ClothingItems
     */
    public List<ClothingItem> getQuizResults(String preferredGender, String preferredOccasion) {
        List<ClothingItem> allItems = DataStore.getAllItems();
        List<ClothingItem> matches = new ArrayList<>();

        for (ClothingItem item : allItems) {
            // Check if gender matches and if the occasion string contains the user's choice
            boolean genderMatch = item.getGender().equalsIgnoreCase(preferredGender);
            boolean occasionMatch = item.getOccasion().toLowerCase().contains(preferredOccasion.toLowerCase());

            if (genderMatch && occasionMatch) {
                matches.add(item);
            }
        }
        return matches;
    }

    /**
     * Formats the quiz results into a readable string for the UI.
     */
    public String formatQuizResult(List<ClothingItem> results) {
        if (results.isEmpty()) {
            return "No perfect match found! Try exploring different regions in the main gallery.";
        }

        StringBuilder sb = new StringBuilder("### We found " + results.size() + " matches for you! ###\n");
        for (ClothingItem item : results) {
            sb.append("- ").append(item.getName())
              .append(" (Region: ").append(item.getRegion()).append(")\n")
              .append("  Description: ").append(item.getDescription()).append("\n\n");
        }
        return sb.toString();
    }
}

import java.util.*;

public class Feature6Service {

    public static class Question {
        public String question;
        public List<String> options;
        public String answer;

        public Question(String q, List<String> o, String a) {
            question = q;
            options = o;
            answer = a;
        }
    }

    public List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question("Which fabric is known as the 'Fabric of Freedom'?",
                Arrays.asList("Silk", "Khadi", "Cotton", "Linen"), "Khadi"));

        list.add(new Question("Paithani saree belongs to which state?",
                Arrays.asList("Gujarat", "Maharashtra", "Punjab", "Kerala"), "Maharashtra"));

        list.add(new Question("Bandhani is a type of?",
                Arrays.asList("Weaving", "Dyeing", "Printing", "Knitting"), "Dyeing"));

        list.add(new Question("Kalamkari is known for?",
                Arrays.asList("Painting", "Weaving", "Dyeing", "Knitting"), "Painting"));

        list.add(new Question("Which garment is traditionally worn by men?",
                Arrays.asList("Lehenga", "Dhoti", "Saree", "Salwar"), "Dhoti"));

        list.add(new Question("Banarasi sarees are famous for?",
                Arrays.asList("Cotton", "Silk", "Wool", "Linen"), "Silk"));

        list.add(new Question("Which state is famous for Bandhani?",
                Arrays.asList("Rajasthan", "Kerala", "Bihar", "Assam"), "Rajasthan"));

        list.add(new Question("Khadi is made using?",
                Arrays.asList("Machines", "Hand-spinning", "Chemical process", "Synthetic fibers"),
                "Hand-spinning"));

        return list;
    }
}
 main
