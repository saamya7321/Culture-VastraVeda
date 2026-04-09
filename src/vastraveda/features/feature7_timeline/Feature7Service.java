package vastraveda.features.feature7_timeline;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Feature 7 — Historical Timeline Service
 * Provides business logic for chronological sorting and era-based filtering.
 */
public class Feature7Service {

    /**
     * Retrieves all items sorted by their historical era.
     * Note: This assumes the 'era' string can be sorted alphabetically or 
     * follows a recognizable pattern in your DataStore.
     */
    public List<ClothingItem> getTimelineItems() {
        return DataStore.getAllItems().stream()
                .sorted(Comparator.comparing(ClothingItem::getEra))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    // Reverse if you want Modern items first, or keep as is for Ancient first
                    return list;
                }));
    }

    /**
     * Filters garments by a specific historical period.
     * @param eraName The era to filter by (e.g., "Ancient", "Medieval", "Vedic")
     */
    public List<ClothingItem> getItemsByEra(String eraName) {
        return DataStore.getAllItems().stream()
                .filter(item -> item.getEra().equalsIgnoreCase(eraName))
                .collect(Collectors.toList());
    }

    /**
     * Search logic specifically for the timeline view.
     */
    public List<ClothingItem> searchTimeline(String query) {
        String lowerQuery = query.toLowerCase();
        return DataStore.getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerQuery) || 
                                 item.getEra().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}