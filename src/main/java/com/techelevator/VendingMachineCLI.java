package com.techelevator;

import com.techelevator.view.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

	private final Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	String pattern = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());

	public void run() {
		String choice;
		do {
			choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				File file = new File("vendingmachine.csv");
				try {
					Scanner scanner = new Scanner(file);
					inventory = ((inventory == null) ? new Inventory() : inventory);
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
						System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty: " + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()));
						index++;
					}
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				label:
				while (true) {
					System.out.println(System.lineSeparator() + SECOND_MENU_OPTION_CURRENT_MONEY_PROVIDED + " $" + BigDecimal.valueOf(Double.parseDouble(amount)).setScale(2));
					String secondChoice = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS);

					if (secondChoice.equals(SECOND_MENU_OPTION_FEED_MONEY)) {

						System.out.print(System.lineSeparator() + "Type the amount of money you want to feed in WHOLE DOLLAR amounts: ");
						Scanner scanner = new Scanner(System.in);
						if (scanner.hasNext()) {
							Double prevAmt = Double.valueOf(amount);
							Double currAmt;
							amount = scanner.next();

							try {
								currAmt = (double) Integer.parseInt(amount);
							} catch (Exception e) {
								System.out.println(System.lineSeparator() + "*** " + amount + " is not a valid option ***" + System.lineSeparator());
								amount = String.valueOf(prevAmt);
								continue;
							}
							currAmt = prevAmt + currAmt;
							new BigDecimal(currAmt).setScale(2, RoundingMode.HALF_UP).doubleValue();
							System.out.println(Financial.log("FEED MONEY", "$" + amount + " $" + currAmt));
							Financial.log("FEED MONEY", BigDecimal.valueOf(currAmt - prevAmt).setScale(2).toString(), BigDecimal.valueOf(currAmt).setScale(2).toString());
							amount = String.valueOf(currAmt);
						}
						//scanner.close();

					}
					switch (secondChoice) {
						case SECOND_MENU_OPTION_SELECT_PRODUCT: {
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
									System.out.println(System.lineSeparator() + pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty: " + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()));
									index++;
								}
							} catch (FileNotFoundException e) {
								System.out.println(e.getMessage());
							}
							System.out.print(System.lineSeparator() + "Type the slot number to dispense the item: ");
							Scanner scanner = new Scanner(System.in);
							String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);
							if (scanner.hasNext()) {
								String item = scanner.nextLine();
								boolean doesProductExist = false;
								for (String key : keys) {

									Product product = inventory.getInventoryMap().get(key);
									String productSlot = product.getSlot();
									if (productSlot.equalsIgnoreCase(item)) {
										doesProductExist = true;
										String productType = product.getProductType();
										BigDecimal price = product.getPrice();
										BigDecimal bdAmt = BigDecimal.valueOf(Double.parseDouble(amount));
										if (bdAmt.compareTo(price) >= 0) {
											amount = bdAmt.subtract(price).toString();

											Financial.log(product.getProductName() + " " + product.getSlot(), bdAmt + " " + amount);
											//Financial.log(product , price.setScale(2).toString(), amount);
											switch (productType) {

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
										}
									}

								}
								if (!doesProductExist) {
									System.out.println("Invalid Code!");
								}
//								if(!inventory.isProductInStock) {
//									System.out.println("SOLD OUT!");
//								}
							}

							break;
						}
						case SECOND_MENU_OPTION_FINISH_TRANSACTION:

							System.out.println(Financial.returnChange(amount));
							Financial.log("GIVE CHANGE", String.valueOf(BigDecimal.valueOf(Double.parseDouble(amount)).setScale(2)), "0.00");

							amount = "0.00";
							break label;

						case SECOND_MENU_OPTION_SALES_REPORT: {

							String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);
							int index = 0;
							File salesReport = new File("salesReport.txt");
							try (PrintWriter writer = new PrintWriter(salesReport)) {
								for (Map.Entry<String, Product> entry : inventory.getInventoryMap().entrySet()) {
									Product pr = inventory.getInventoryMap().get(keys[index]);
									writer.print(pr.getProductName() + "|" + (5 - pr.getQuantity()) + "\n");
								}
								writer.print("\n" + "**TOTAL SALES** " + "$" + amount);
							} catch (IOException ex) {
								System.out.println("Exception: " + ex.getMessage());
							}
							break;
						}
					}
				}
			}
		} while (!choice.equals(MAIN_MENU_OPTION_EXIT));

	}


	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
