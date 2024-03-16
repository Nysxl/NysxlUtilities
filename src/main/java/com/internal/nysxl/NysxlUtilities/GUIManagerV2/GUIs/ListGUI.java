package com.internal.nysxl.NysxlUtilities.GUIManagerV2.GUIs;

import com.internal.nysxl.NysxlUtilities.GUIManagerV2.Buttons.Button;
import com.internal.nysxl.NysxlUtilities.ItemBuilder.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * A GUI class that supports pagination and dynamic lists of items.
 */
public class ListGUI extends GUI {

    private final List<Integer> listSlots = new ArrayList<>();
    private int currentPage = 0;
    private final Map<String, Button> listItems = new HashMap<>();
    private int itemsPerPage = 28; // Adjust based on your layout
    private final ItemStack nextButton = new ItemFactory(Material.ARROW).setItemDisplayName("Next Page").buildItem();
    private final ItemStack prevButton = new ItemFactory(Material.ARROW).setItemDisplayName("Previous Page").buildItem();
    private final int nextButtonSlot = 50;
    private final int prevButtonSlot = 48;

    /**
     * Constructs a new ListGUI with a specified title and size.
     * @param title The title of the GUI.
     * @param size The size of the GUI, which should be a multiple of 9.
     */
    public ListGUI(String title, int size) {
        super(title, size);
        initializeListSlots();
    }

    /**
     * Initializes the slots within the GUI to be used for displaying the list items.
     */
    private void initializeListSlots() {
        for (int i = 10; i <= 16; i++) listSlots.add(i);
        for (int i = 19; i <= 25; i++) listSlots.add(i);
        for (int i = 28; i <= 34; i++) listSlots.add(i);
        updateNavigationButtons();
    }

    /**
     * Adds an item to the list within the GUI.
     * @param button The Button to add as a list item.
     */
    public void addListItem(Button button) {
        listItems.put(UUID.randomUUID().toString(), button);
        updateListDisplay();
    }

    /**
     * Updates the display of the list within the GUI to reflect any changes or navigation.
     */
    private void updateListDisplay() {
        clearListSlots();
        int startIndex = currentPage * itemsPerPage;
        List<Button> itemList = new ArrayList<>(listItems.values());
        for (int i = startIndex; i < Math.min(startIndex + itemsPerPage, itemList.size()); i++) {
            Button button = itemList.get(i);
            if (button != null) {
                addButton(button.withSlot(listSlots.get(i - startIndex))); // Ensure button knows its slot
            }
        }
        updateNavigationButtons();
    }

    /**
     * Clears items from the list slots in preparation for an update or page change.
     */
    private void clearListSlots() {
        listSlots.forEach(slot -> super.getInventory().clear(slot));
    }

    /**
     * Updates or removes the navigation buttons based on the current page and total pages.
     */
    private void updateNavigationButtons() {
        int totalPages = getMaxPages();

        // "Previous" page button
        if (currentPage > 0) {
            Button prevPageButton = new Button(prevButton)
                    .withSlot(prevButtonSlot)
                    .withAction(Button.MouseClick.LEFT_CLICK, player -> {
                        navigatePages(-1);
                        updateListDisplay();
                        refreshInventory(player);
                        return "";
                    });
            addButton(prevPageButton);
        } else {
            removeButton(prevButtonSlot);
        }

        // "Next" page button
        if (currentPage < totalPages - 1) {
            Button nextPageButton = new Button(nextButton)
                    .withSlot(nextButtonSlot)
                    .withAction(Button.MouseClick.LEFT_CLICK, player -> {
                        navigatePages(1);
                        updateListDisplay();
                        refreshInventory(player);
                        return "";
                    });
            addButton(nextPageButton);
        } else {
            removeButton(nextButtonSlot);
        }
    }

    /**
     * Refreshes the GUI inventory for a player, useful after list updates or navigation actions.
     * @param player The player for whom the inventory should be refreshed.
     */
    private void refreshInventory(Player player) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        inv.clear(); // Be cautious with clearing, you may need a more nuanced approach
        initializeFill(); //adds the background fill to the inventory.
        addAllListButtons(); // Re-add all buttons including dynamic list items and navigation buttons
        player.updateInventory(); // Make sure the client sees the update
    }

    /**
     * Adds all buttons to the GUI, including static, dynamic, and navigation buttons.
     */
    private void addAllListButtons() {
        listItems.values().forEach(this::addButton);
        updateNavigationButtons(); // Ensure navigation buttons are correctly placed
    }

    /**
     * Calculates the maximum number of pages based on the number of items and the items per page.
     * @return The total number of pages.
     */
    private int getMaxPages() {
        return (int) Math.ceil((double) listItems.size() / itemsPerPage);
    }

    /**
     * opens the gui for the player.
     * @param player the player to open the gui for.
     */
    @Override
    public void open(Player player){
        refreshInventory(player);
    }

    /**
     * clears all items from the listItems array.
     */
    public void clearListItems(){
        listItems.clear();
    }

    /**
     * Navigates the pages of the GUI list.
     * @param direction The direction to navigate: positive for next, negative for previous.
     */
    private void navigatePages(int direction) {
        currentPage += direction;
        updateListDisplay();
    }

    @Override
    public String executeAction(InventoryClickEvent e) {
        // Implement action execution logic for clicked items here
        return "";
    }

}
