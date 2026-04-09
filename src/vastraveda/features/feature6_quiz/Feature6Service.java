package vastraveda.features.feature6_quiz;

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
