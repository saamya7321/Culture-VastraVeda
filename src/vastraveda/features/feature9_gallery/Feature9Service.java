package vastraveda.features.feature9_gallery;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;
 update-datastore
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
     * Alias for compatibility with the main branch.
     */
    public List<ClothingItem> getAllGarments() {
        return getAllItems();
    }

    /**
     * Groups clothing items by their region.
     */
    public Map<String, List<ClothingItem>> getGalleryByRegion() {
        return DataStore.getAllItems().stream()
                .collect(Collectors.groupingBy(ClothingItem::getRegion));
    }

    /**
     * Filters the gallery based on the material/fabric.
     */
    public List<ClothingItem> filterByFabric(String fabricType) {
        List<ClothingItem> filteredList = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            // This will now work because we added getFabric() to ClothingItem.java
            if (item.getFabricType().equalsIgnoreCase(fabricType)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    /**
     * Searches the gallery for a specific keyword.
     */
    public List<ClothingItem> searchGallery(String keyword) {
        String query = keyword.toLowerCase();
        return DataStore.getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(query) ||
                                item.getRegion().toLowerCase().contains(query) ||
                                item.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    /**
     * Required by Main branch - Retrieves detail for a specific item by name.
     */
=======

public class Feature9Service {

    public List<ClothingItem> getAllGarments() {
        return DataStore.getAllItems();
    }

 main
    public ClothingItem getItemDetail(String name) {
        return DataStore.getAllItems().stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
 update-datastore

    public List<ClothingItem> filterItems(String text, String selectedItem) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'filterItems'");
    }

 main
}