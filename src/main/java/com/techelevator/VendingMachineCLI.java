package com.techelevator;

import com.techelevator.view.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				File file = new File("vendingmachine.csv");
				try {
					Scanner scanner = new Scanner(file);
					Inventory inventory = new Inventory();;
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						String[] data = line.split("\\|");
						Product product = new Product(data[0], data[1], Double.valueOf(data[2]), data[3], 5);

						inventory.getInventoryMap().put(data[1], product);

					}
					String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);

					int index = 0;
					for (Map.Entry<String, Product> map : inventory.getInventoryMap().entrySet()) {
						Product pr = inventory.getInventoryMap().get(keys[index]);
						System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty:" + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()) );
						index++;
					}
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
