package vastraveda.features.feature9_gallery;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Feature 9 — Visual Gallery Service
 * Provides business logic for organizing and filtering the cultural gallery.
 */
public class Feature9Service {

    /**
     * Retrieves all items currently in the DataStore for the gallery view.
     */
    public List<ClothingItem> getAllItems() {
        return DataStore.getAllItems();
    }

    /**
     * Groups clothing items by their region (e.g., "North India", "South India").
     * Useful for creating categorized sections in the UI.
     */
    public Map<String, List<ClothingItem>> getGalleryByRegion() {
        return DataStore.getAllItems().stream()
                .collect(Collectors.groupingBy(ClothingItem::getRegion));
    }

    /**
     * Filters the gallery based on the material/fabric.
     * @param fabricType The type of fabric (e.g., "Silk", "Cotton")
     */
    public List<ClothingItem> filterByFabric(String fabricType) {
        List<ClothingItem> filteredList = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getFabric().equalsIgnoreCase(fabricType)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    /**
     * Searches the gallery for a specific keyword.
     * Matches against name, region, or description.
     */
    public List<ClothingItem> searchGallery(String keyword) {
        String query = keyword.toLowerCase();
        return DataStore.getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(query) ||
                                item.getRegion().toLowerCase().contains(query) ||
                                item.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }
}