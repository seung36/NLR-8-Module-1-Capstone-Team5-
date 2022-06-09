package com.techelevator;

import com.techelevator.view.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,MAIN_MENU_OPTION_EXIT };

	private static final String SECOND_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String SECOND_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String SECOND_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String SECOND_MENU_OPTION_CURRENT_MONEY_PROVIDED = "Current Money Provided";
	private static final String SECOND_MENU_OPTION_SALES_REPORT = " ";
	private static final String[] SECOND_MENU_OPTIONS = { SECOND_MENU_OPTION_FEED_MONEY, SECOND_MENU_OPTION_SELECT_PRODUCT, SECOND_MENU_OPTION_FINISH_TRANSACTION, SECOND_MENU_OPTION_SALES_REPORT };

	private String amount = "0.00";

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
						//TODO fix decimals so 0 shows in the hundredths position
						System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty:" + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()) );
						index++;
					}
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					System.out.println(SECOND_MENU_OPTION_CURRENT_MONEY_PROVIDED + " " + amount);
					String secondChoice = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS);

					if (secondChoice.equals(SECOND_MENU_OPTION_FEED_MONEY)) {
						//menu.feedmoney();
						System.out.println("Type the amount of money you want to feed in whole dollar amounts: ");
						Scanner scanner = new Scanner(System.in);
						if (scanner.hasNext()) {
							amount = scanner.next();
						}

					}/* else if (secondChoice.equals(SECOND_MENU_OPTION_SELECT_PRODUCT)) {
						menu.purchase();
					} else if (secondChoice.equals(SECOND_MENU_OPTION_FINISH_TRANSACTION)) {
						menu.finish();
						run();
					} else if (secondChoice.equals(SECOND_MENU_OPTION_SALES_REPORT)) {
						menu.generateSalesReport
					}*/
				}
			}
			else if (choice.equals(MAIN_MENU_OPTION_EXIT));
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
