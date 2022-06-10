package com.techelevator;

import com.techelevator.view.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	Inventory inventory;

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	String pattern = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				File file = new File("vendingmachine.csv");
				try {
					Scanner scanner = new Scanner(file);
					inventory = new Inventory();
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						String[] data = line.split("\\|");//{"A1", "Potato Crisps", "3.05", "Chip"}
						Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3], 5);

						inventory.getInventoryMap().put(data[1], product);

					}
					scanner.close();
					String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);

					int index = 0;
					for (Map.Entry<String, Product> map : inventory.getInventoryMap().entrySet()) {
						Product pr = inventory.getInventoryMap().get(keys[index]);
						//TODO fix decimals so 0 shows in the hundredths position
						System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty: " + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()) );
						index++;
					}
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					System.out.println(SECOND_MENU_OPTION_CURRENT_MONEY_PROVIDED + " $" + amount);
					String secondChoice = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS);

					if (secondChoice.equals(SECOND_MENU_OPTION_FEED_MONEY)) {
						//menu.feedmoney();
						System.out.print("Type the amount of money you want to feed in whole dollar amounts: ");
						Scanner scanner = new Scanner(System.in);
						if (scanner.hasNext()) {
							Double prevAmt = Double.valueOf(amount);
							amount = scanner.next();
							//String pattern = "yyyy-MM-dd";
							//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
							//String date = simpleDateFormat.format(new Date());
							Double currAmt = Double.valueOf(amount);
							currAmt = prevAmt + currAmt;
							System.out.println(Financial.log("FEED MONEY", "$" + amount + ": $" + currAmt));
							amount = String.valueOf(currAmt);
						}
						//scanner.close();

					} if (secondChoice.equals(SECOND_MENU_OPTION_SELECT_PRODUCT)) {
						//menu.purchase();
						//if (inventory.inventorySize() == 0) {
						File file = new File("vendingmachine.csv");
						try {
							if (inventory == null) {
								Scanner scanner = new Scanner(file);
								inventory = new Inventory();
								while (scanner.hasNextLine()) {
									String line = scanner.nextLine();
									String[] data = line.split("\\|");
									Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3], 5);

									inventory.getInventoryMap().put(data[1], product);

								}
								scanner.close();
							}
							String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);

							int index = 0;
							for (Map.Entry<String, Product> map : inventory.getInventoryMap().entrySet()) {
								Product pr = inventory.getInventoryMap().get(keys[index]);
								//TODO fix decimals so 0 shows in the hundredths position
								System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty: " + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()) );
								index++;
							}
						} catch (FileNotFoundException e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Type the slot number to dispense the item: ");
						Scanner scanner = new Scanner(System.in);
						String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);
						if (scanner.hasNext()) {
							String item = scanner.nextLine();
							boolean doesProductExist = false;
							for (String key: keys) {

								Product product = inventory.getInventoryMap().get(key);
								String productSlot = product.getSlot();
								if (productSlot.equals(item)) {
									doesProductExist = true;
									String productType = product.getProductType();
									BigDecimal price = product.getPrice();
									BigDecimal bdAmt = BigDecimal.valueOf(Double.parseDouble(amount));
									if (bdAmt.compareTo(price) >= 0) {
										amount = bdAmt.subtract(price).toString();
										Financial.log(product.getProductName() + " " + product.getSlot(), bdAmt.toString() + " " + amount);
										switch(productType) {
											case "Chip":
												System.out.println("Crunch Crunch, Crunch!");
												break;
											case "Gum":
												System.out.println("Chew Chew, Pop!");
												break;
											case "Drink":
												System.out.println("Cheers Glug, Glug!");
												break;
											case "Candy":
												System.out.println("Munch Munch, Mmm-Good!");
												break;
											default:
										}
									product.decreaseQuantity();
									} else {

									}
								}

							}
							if (!doesProductExist) {
								System.out.println("Invalid Code!");
							}
						}

					} else if (secondChoice.equals(SECOND_MENU_OPTION_FINISH_TRANSACTION)) {
						//menu.finish();
						//run();
						System.out.println(Financial.returnChange(amount));
						amount = "0.00";
						break;

					} else if (secondChoice.equals(SECOND_MENU_OPTION_SALES_REPORT)) {
							String filePath = date;
							File salesReport = new File(filePath +"salesReport.txt");
							try (PrintWriter writer = new PrintWriter(salesReport)) {
								for (Map.Entry<String, Integer> entry : Inventory.getInventoryMap().entryset()) {
								String key = entry.getKey(data[1]);
								String value = String.valueOf(entry.getValue(data[4]));
								writer.print(key + "|" + value + "\n");
							}
							writer.print("\n" +"**TOTAL SALES** " + String.format("$%,.2f", amount));
							} catch (IOException ex) {
								System.out.println("Exception");
							}
						}

					}
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
