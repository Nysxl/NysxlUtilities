package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicButton;
import com.internal.nysxl.NysxlUtilities.Utility.SearchHandler;
import com.internal.nysxl.NysxlUtilities.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a dynamic list GUI in a Minecraft plugin that supports pagination and the dynamic addition of items.
 * This GUI can be used to display a list of items to the player, with the ability to navigate through pages and
 * perform specific actions when items are clicked.
 */
public class DynamicListGUI extends DynamicGUI {

    // List of slot indexes in the GUI where list items will be displayed.
    private List<Integer> listSlots = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43);
    // The current page number the GUI is displaying.
    private int currentPage = 0;
    // The list of all DynamicButtons representing items added to the GUI.
    private final List<DynamicButton> listItems = new ArrayList<>();
    // A temporary list of DynamicButtons used for display purposes, allowing for filtering without altering the original list.
    private List<DynamicButton> displayItems = new ArrayList<>();
    // The number of items to display per page.
    private int itemsPerPage;
    // Slot index for the 'Next' navigation button.
    private final int nextButtonSlot = 50;
    // Slot index for the 'Previous' navigation button.
    private final int prevButtonSlot = 48;
    // ItemStack representing the 'Next' navigation button.
    private final ItemStack nextButton = new ItemStack(Material.ARROW);
    // ItemStack representing the 'Previous' navigation button.
    private final ItemStack prevButton = new ItemStack(Material.ARROW);
    // ItemStack slot representing the 'Search' button.
    private final int searchCompassSlot = 49;
    // ItemStack slot representing the 'Reset' button.
    private final int resetBarrierSlot = 45;


    /**
     * Constructs a new DynamicListGUI with a specified name and size.
     *
     * @param name The name of the GUI.
     * @param size The size of the inventory in slots.
     */
    public DynamicListGUI(String name, int size) {
        super(name, size);
        globalInitialize();
    }

    /**
     * Constructs a new DynamicListGUI with a specified name, size, and a specific item used to fill the background.
     *
     * @param name     The name of the GUI.
     * @param size     The size of the inventory in slots.
     * @param fillItem The ItemStack used to fill the background of the GUI.
     */
    public DynamicListGUI(String name, int size, ItemStack fillItem) {
        super(name, size, fillItem);
        globalInitialize();
    }

    /**
     * add all initializers in here that will be run regardless of constructor
     */
    private void globalInitialize(){
        this.itemsPerPage = listSlots.size();
        initializeNavigationButtons();
        initializeCompass();
        initializeResetSearchButton();
    }

    /**
     * Sets the slots that will be used for displaying list items. This method allows for customization of item placement within the GUI.
     *
     * @param listSlots An array of integers representing the slot indices where list items should be displayed.
     */
    public void setListSlots(int[] listSlots) {
        this.listSlots = Arrays.asList(Arrays.stream(listSlots).boxed().toArray(Integer[]::new));
        this.itemsPerPage = listSlots.length;
    }

    /**
     * Initializes the navigation buttons ('Next' and 'Previous') for the GUI, enabling page navigation.
     */
    private void initializeNavigationButtons() {
        addButton(nextButtonSlot, nextButton, player -> navigatePages(1));
        addButton(prevButtonSlot, prevButton, player -> navigatePages(-1));
    }

    /**
     * updates the list prior to opening the gui for the player.
     * @param player The player for whom the GUI should be opened.
     */
    @Override
    public void open(Player player){
        updateList();
        super.open(player);
    }

    /**
     * initializes the search function in the gui.
     */
    private void initializeCompass() {
        ItemStack searchCompass = new ItemStack(Material.COMPASS);
        ItemMeta meta = searchCompass.getItemMeta();
        if(meta != null) {
            meta.setDisplayName("Search");
            searchCompass.setItemMeta(meta);
        }

        addButton(searchCompassSlot, searchCompass, player -> {
            SearchHandler.startSearchInteraction(player, (p, searchTerm) -> {
                p.closeInventory();
                Bukkit.getScheduler().runTask(main.getInstance(), () -> {
                    this.searchItems(searchTerm);
                    this.open(p);
                });
            });
        });
    }

    /**
     * Navigates between pages of items in the GUI.
     *
     * @param direction The direction to navigate: 1 for next page, -1 for previous page.
     */
    public void navigatePages(int direction) {
        int newPage = currentPage + direction;
        if (newPage >= 0 && newPage * itemsPerPage < displayItems.size()) {
            currentPage = newPage;
            updateList();
            updateNavigationButtonVisibility();
        }
    }

    /**
     * Dynamically updates the visibility of the next and previous buttons based on the current page and the total number of items.
     */
    private void updateNavigationButtonVisibility() {
        getInventory().setItem(prevButtonSlot, currentPage > 0 ? prevButton : new ItemStack(Material.AIR));
        getInventory().setItem(nextButtonSlot, (currentPage + 1) * itemsPerPage < displayItems.size() ? nextButton : new ItemStack(Material.AIR));
    }

    /**
     * Adds an item to the GUI list with a specified click action.
     *
     * @param item   The ItemStack to be added as a list item.
     * @param action The action to be performed when the item is clicked by a player.
     */
    public void addItem(ItemStack item, Consumer<Player> action) {
        listItems.add(new DynamicButton(0, item, action));
        displayItems = new ArrayList<>(listItems); // Ensure displayItems matches listItems
    }

    /**
     * Updates the list display based on the current page and the number of items per page, showing only the relevant items.
     */
    public void updateList() {
        clearList();
        getCurrentPageItems().forEach(this::addButtonToSlot);
    }

     /**
     * Adds a button to the first available slot in the listSlots and then removes that slot from consideration to maintain pagination integrity.
     *
     * @param button The DynamicButton to add to the GUI.
     */
    private void addButtonToSlot(DynamicButton button) {
        listSlots.stream().limit(1).forEach(slot -> addButton(slot, button.getButton(), button.getAction()));
        listSlots.remove(0); // Remove the slot to maintain pagination correctness
    }

    /**
     * Retrieves the list of items that should be displayed on the current page.
     *
     * @return A list of DynamicButtons representing the items on the current page.
     */
    private List<DynamicButton> getCurrentPageItems() {
        int start = currentPage * itemsPerPage;
        return displayItems.stream()
                .skip(start)
                .limit(itemsPerPage)
                .collect(Collectors.toList());
    }

    /**
     * Filters the list items based on a provided search query. The search checks both the item's name and lore for matches.
     *
     * @param query The search query string.
     */
    public void searchItems(String query) {
        String lowerCaseQuery = query.toLowerCase();
        displayItems = listItems.stream()
                .filter(item -> itemMatchesQuery(item.getButton(), lowerCaseQuery))
                .collect(Collectors.toList());
        currentPage = 0;
        updateList();
    }

    /**
     * Checks if an ItemStack matches the search query by checking its name and lore.
     *
     * @param item  The ItemStack to check.
     * @param query The search query.
     * @return True if the item matches the query, false otherwise.
     */
    private boolean itemMatchesQuery(ItemStack item, String query) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.getDisplayName().toLowerCase().contains(query)) return true;
            if (meta.hasLore()) {
                for (String loreLine : meta.getLore()) {
                    if (loreLine.toLowerCase().contains(query)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Resets the search filter and displays the original list of items.
     */
    public void resetSearch() {
        displayItems = new ArrayList<>(listItems); // Reset displayItems to the original list
        currentPage = 0; // Optionally reset to the first page
        updateList(); // Update the GUI display
    }

    /**
     * Initializes the reset search button in the GUI.
     */
    private void initializeResetSearchButton() {
        ItemStack resetSearchButton = new ItemStack(Material.BARRIER); // Choose an appropriate material for the reset button
        ItemMeta meta = resetSearchButton.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Reset Search");
            resetSearchButton.setItemMeta(meta);
        }

        addButton(resetBarrierSlot, resetSearchButton, player -> {
            resetSearch();
            open(player);
        });
    }

    /**
     * Clears the list display slots, preparing them for an updated list of items.
     */
    private void clearList() {
        listSlots.forEach(slot -> getInventory().setItem(slot, new ItemStack(Material.AIR)));
    }
}
