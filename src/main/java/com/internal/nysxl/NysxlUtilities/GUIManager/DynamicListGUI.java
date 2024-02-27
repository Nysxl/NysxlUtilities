package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a dynamic list GUI that supports pagination and dynamic item addition.
 */
public class DynamicListGUI extends DynamicGUI {

    private int[] listSlots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
    private int currentPage = 0;
    private final List<DynamicButton> listItems = new ArrayList<>();
    private List<DynamicButton> displayItems = new ArrayList<>(); // Temporary list for display purposes
    private int itemsPerPage;
    private final int nextButtonSlot = 50; // Slot for the 'Next' button
    private final int prevButtonSlot = 48; // Slot for the 'Previous' button
    private final ItemStack nextButton = new ItemStack(Material.ARROW); // Customize as needed
    private final ItemStack prevButton = new ItemStack(Material.ARROW); // Customize as needed

    /**
     * Constructs a DynamicListGUI with a specified name and size.
     *
     * @param name The name of the GUI.
     * @param size The size of the inventory (number of slots).
     */
    public DynamicListGUI(String name, int size) {
        super(name, size);
        this.itemsPerPage = listSlots.length;
        initializeNavigationButtons();
    }

    /**
     * Constructs a DynamicListGUI with a specified name, size, and fill item.
     *
     * @param name     The name of the GUI.
     * @param size     The size of the inventory (number of slots).
     * @param fillItem The ItemStack used to fill the background with in the GUI.
     */
    public DynamicListGUI(String name, int size, ItemStack fillItem) {
        super(name, size, fillItem);
        this.itemsPerPage = listSlots.length;
        initializeNavigationButtons();
    }

    /**
     * Sets the slots used for displaying the list items.
     *
     * @param listSlots An array of slot indices where list items are displayed.
     */
    public void setListSlots(int[] listSlots) {
        this.listSlots = listSlots;
        this.itemsPerPage = listSlots.length;
    }

    /**
     * Initializes the navigation buttons for paging through the list.
     */
    private void initializeNavigationButtons() {
        // Previous Button
        addButton(prevButtonSlot, prevButton, player -> {
            if (currentPage > 0) {
                currentPage--;
                updateList();
                updateNavigationButtonVisibility();
            }
        });

        // Next Button
        addButton(nextButtonSlot, nextButton, player -> {
            if ((currentPage + 1) * itemsPerPage < displayItems.size()) {
                currentPage++;
                updateList();
                updateNavigationButtonVisibility();
            }
        });
    }

    /**
     * dynamically updates the next and previous buttons to either show or not show them depending on if there is or isn't a
     * next page button.
     */
    private void updateNavigationButtonVisibility() {
        getInventory().setItem(prevButtonSlot, currentPage > 0 ? prevButton : new ItemStack(Material.AIR));
        getInventory().setItem(nextButtonSlot, (currentPage + 1) * itemsPerPage < displayItems.size() ? nextButton : new ItemStack(Material.AIR));
    }

    /**
     * Adds an item with a specified action to the list.
     *
     * @param item   The ItemStack to add as a list item.
     * @param action The action to perform when the item is clicked by a player.
     */
    public void addItem(ItemStack item, Consumer<Player> action) {
        listItems.add(new DynamicButton(0, item, action));
        displayItems = new ArrayList<>(listItems); // Ensure displayItems matches listItems
    }

    /**
     * Updates the list display based on the current page and items per page.
     */
    public void updateList() {
        clearList();
        List<DynamicButton> pageItems = getCurrentPageItems();
        for (int i = 0; i < pageItems.size(); i++) {
            int slot = listSlots[i];
            DynamicButton button = pageItems.get(i);
            addButton(slot, button.getButton(), button.getAction());
        }
        updateGUI(); // Update the entire GUI including the list
    }

    /**
     * Retrieves the list items for the current page.
     *
     * @return A list of DynamicButtons representing the current page items.
     */
    private List<DynamicButton> getCurrentPageItems() {
        int start = currentPage * itemsPerPage;
        return displayItems.stream()
                .skip(start)
                .limit(itemsPerPage)
                .collect(Collectors.toList());
    }

    /**
     * Filters the list items based on a search query that checks both the item's name and lore.
     *
     * @param query The search query.
     */
    public void searchItems(String query) {
        String lowerCaseQuery = query.toLowerCase();
        displayItems = listItems.stream()
                .filter(item -> {
                    ItemMeta meta = item.getButton().getItemMeta();
                    if (meta != null) {
                        if (meta.getDisplayName().toLowerCase().contains(lowerCaseQuery)) return true;
                        return meta.getLore() != null && meta.getLore().stream().anyMatch(lore -> lore.toLowerCase().contains(lowerCaseQuery));
                    }
                    return false;
                })
                .collect(Collectors.toList());
        currentPage = 0;
        updateList();
    }

    /**
     * Resets the search filter and displays all items.
     */
    public void resetSearch() {
        displayItems = new ArrayList<>(listItems); // Reset displayItems to show all items
        currentPage = 0;
        updateList();
    }

    /**
     * Clears all the items from the list display.
     */
    public void clearList() {
        for (int slot : listSlots) {
            getInventory().setItem(slot, new ItemStack(Material.AIR));
        }
    }
}
