package com.onlineBook.java;

//************* Must add external jar file else project wont work ***********************************************
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Timestamp;
	import java.time.LocalDate;

	public class OnlineBookStore {

		public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ WELCOME @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("1  --->   Buyer");
			System.out.println("2  --->   Seller");
			System.out.println("3  --->   Sign Up");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.print("\t\t Please Choose any one... :");
			int choice = Integer.parseInt(br.readLine());
	        //buyer era
			if (choice == 1) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.print("\t\t Enter Buyer username:");
				String userName = br.readLine();
				System.out.print("\t\t Enter Buyer password:");
				String passWord = br.readLine();
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement("select * from buyer where userName=?");
				ps.setString(1, userName);
				ResultSet result = ps.executeQuery();
				String userPassWord = null;
				int userId = 0;
				String add = "null";
				while (result.next()) {
					userPassWord = result.getString("passWord");
					userId = result.getInt("buyerId");
					add = result.getString("address");
				}

				if (passWord.equals(userPassWord)) {

					System.out.println("You have successfully logged in!!");

					boolean login = true;
					do {

						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@  Hello " + userName.toUpperCase()
								+ " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("1) Show Book \n 2) Search Book\n 3) Order Book\n 4)LogOut");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.print("\t\t Choice? :");
						int operationNumber = Integer.parseInt(br.readLine());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						String status = null;
						switch (operationNumber) {
						//books_qty>0
						case 1:
							System.out.println("Show Books");
							try {
								ps = conn.prepareStatement("SELECT * FROM books where quantity>0");
								result = ps.executeQuery();
								if (result.next()) {
									do {
										bookDetails(result);
									} while (result.next());
								} else {
									System.out.println("Books not found");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login = false;
							}
							break;
						//Filter of Books
						case 2:
							System.out.println("Book Search filter");
							System.out.println("1) Book Name \n 2) Book Genre \n 3) Author");
							System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							System.out.print("\t\t Enter your filter choice:");
							int filterNum = Integer.parseInt(br.readLine());
							System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							System.out.println("Search Books");
							//filter by Name of Book
							if (filterNum == 1) {
								System.out.print("Enter name of Book : ");
								String bookName = br.readLine();
								try {
									ps = conn.prepareStatement("SELECT * FROM books WHERE book_name=? and quantity>0");
									ps.setString(1, bookName);
									result = ps.executeQuery();
									if (result.next()) {
										do {
											bookDetails(result);
										} while (result.next());
									} else {
										System.out.println("Book not Found");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;
							}
							//filter using BookGenre
							else if (filterNum == 2) {
								System.out.print("Enter book Genre: ");
								String bookGenre = br.readLine();
								try {
									ps = conn.prepareStatement("SELECT * FROM books WHERE book_type=? and quantity>0");
									ps.setString(1, bookGenre);
									result = ps.executeQuery();
									if (result.next()) {
										do {
											bookDetails(result);
										} while (result.next());
									} else {
										System.out.println("Book Not Found");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;
							} 
							//filter using AuthorName
							else if (filterNum == 3) {
								System.out.print("Author Name: ");
								String AuthorName = br.readLine();
								try {
									ps = conn.prepareStatement("SELECT * FROM books WHERE author=? and quantity>0");
									ps.setString(1, AuthorName);
									result = ps.executeQuery();
									if (result.next()) {
										do {
											bookDetails(result);
										} while (result.next());
									} else {
										System.out.println("Book Not Found");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;
							} else {
								System.out.println("Please choose Valid Choice...");
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login = false;
							}
							//Book Order
						case 3:
							System.out.println("Book the Order");
							System.out.println(" Book Name? :");
							String bookName = br.readLine();
							System.out.println("Book Quantity? :");
							int quantity = Integer.parseInt(br.readLine());
							if (quantity > 0) {
								ps = conn.prepareStatement("select * from books where book_name=? and quantity>0 ");
								ps.setString(1, bookName);
								result = ps.executeQuery();

								int quantityBook = 0;
								String bookName1 = null;
								int bookId = 0;
								int shopId = 0;
								while (result.next()) {
									quantityBook = result.getInt("quantity");
									bookName1 = result.getString("book_name");
									bookId = result.getInt("book_id");
									shopId = result.getInt("shop_id");
								}
								LocalDate newDate1 = LocalDate.now().plusDays(3);

								if (quantityBook > quantity) {
									quantityBook = quantityBook - quantity;
									ps = conn.prepareStatement("update books set quantity=? where book_name=?");
									ps.setInt(1, quantityBook);
									ps.setString(2, bookName1);

									if (ps.executeUpdate() > 0) {
										System.out.println("delivery");
										ps = conn.prepareStatement("insert into delivery values(?,?,?,?,?,?,?,?)");
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										String deliveryId = "TN" + timestamp.getTime(); // TN3243432432423
										ps.setInt(1, bookId);
										ps.setString(2, deliveryId);
										ps.setInt(3, userId);
										ps.setInt(4, quantityBook);
										ps.setString(5, add);
										ps.setLong(6, shopId);
										ps.setDate(7, new Date(System.currentTimeMillis()));
										ps.setDate(8, Date.valueOf(newDate1));
										ps.executeUpdate();

										System.out.println("Balance has got Updated!!");
									}

									else {
										System.out.println("Something went wrong!!");
									}
								} else {

									System.out.println("Out of Stock!!!");
								}
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login = false;
							}
							break;
						case 4:
							System.out.println("See you Again");
							login = false;
							break;
						default:
							System.out.println("Invalid choice");
							break;
						}
					} while (login);
					{
					}
				} else {
					System.out.println("Invalid password");
				}
			} 
			//seller section
			else if (choice == 2) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@  ENTER LOGIN DETAILS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.print("\t\t Enter your username:");
				String sell_userName = br.readLine();
				System.out.print("\t\t Enter your password:");
				String passWord = br.readLine();
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement("select * from seller where sell_userName=?");
				ps.setString(1, sell_userName);
				ResultSet result = ps.executeQuery();
				String userPassWord = null;

				while (result.next()) {
					userPassWord = result.getString("passWord");
				}

				if (passWord.equals(userPassWord)) {

					System.out.println("You have successfully logged in!!");
					boolean login1 = true;
					do {

						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@  WELCOME " + sell_userName.toUpperCase()
								+ " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("1) Show Book \n 2) Add Book \n 3) Delete Book \n 4)LogOut");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.print("\t\t Choice? :");
						int sellerChoice = Integer.parseInt(br.readLine());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						String status = null;
						switch (sellerChoice) {
							//Show every single Book
						case 1:
							System.out.println("Show Books");
							try {
								ps = conn.prepareStatement("SELECT * FROM books");
								result = ps.executeQuery();
								if (result.next()) {
									do {
										bookDetails(result);
									} while (result.next());
								} else {
									System.out.println("Book Not Found");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login1 = false;
							}
							break;
	                     //add new book 
						case 2:
							System.out.println("Add Books");
							System.out.println("Name of Book?:");
							String BName = br.readLine();

							System.out.println("Name of Author? :");
							String AuName = br.readLine();

							System.out.println(" Book Id? :");
							int bookId = Integer.parseInt(br.readLine());

							System.out.println("Price? :");
							int price = Integer.parseInt(br.readLine());

							System.out.println("Quantity? :");
							int quantity = Integer.parseInt(br.readLine());

							System.out.println("Book Type? :");
							String bookType = br.readLine();
							
							System.out.println("Shop ID? :");
							int shopid = Integer.parseInt(br.readLine());

							ps = conn.prepareStatement("insert into books values (?, ?, ?, ?, ?, ?, ?)");
							ps.setInt(1, bookId);
							ps.setString(2, BName);
							ps.setInt(3, price);
							ps.setInt(4, quantity);
							ps.setString(5, bookType);
							ps.setString(6, AuName);
							ps.setInt(7, shopid);
							if (ps.executeUpdate() > 0) {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								System.out.println("Book added successfully!!");
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

							} else {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								System.out.println("Problem Occur in adding Book!!");
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login1 = false;
							}
							break;
	                     //deleting the books
						case 3:
							System.out.println("Delete the Book");
							System.out.println("Book ID? :");
							int delBookId = Integer.parseInt(br.readLine());
							ps = conn.prepareStatement("delete from books where book_id =?");
							ps.setInt(1, delBookId);
							if (ps.executeUpdate() > 0) {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								System.out.println("Acc has been closed !!");
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							} else {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								System.out.println("Account id does not Found!!");
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							}
							System.out.println("Do you want to continue??(Y/N)");
							status = br.readLine();

							if (status.equals("n") || status.equals("N")) {
								login1 = false;
							}
							break;
						case 4:
							System.out.println("Catch up yaa later buddy !! " + sell_userName.toUpperCase());
							login1 = false;
							break;
						default:
							System.out.println("Invalid choice");
							break;
						}
					} while (login1);
				}
			}
			//new buyer
			else if (choice == 3) {
				System.out.println("Welcome!!!");
				System.out.println(" New user Account");
				System.out.println("User name? :");
				String uName = br.readLine();

				System.out.println("User ID? :");
				int uId = Integer.parseInt(br.readLine());

				System.out.println("Password? :");
				String passWORD = br.readLine();

				System.out.println("Residential Address? :");
				String address = br.readLine();

				System.out.println("Mobile no.? :");
				Long phNum = Long.parseLong(br.readLine());
				Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement("select * from buyer ");
				ResultSet result = ps.executeQuery();
				ps = conn.prepareStatement("insert into buyer values (?, ?, ?, ?, ?)");
				ps.setInt(1, uId);
				ps.setString(2, uName);
				ps.setString(3, passWORD);
				ps.setString(4, address);
				ps.setLong(5, phNum);

				if (ps.executeUpdate() > 0) {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println("Account has been created!!");
					System.out.println("==============================================================================");

				} else {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println("Problem occured while creating Acc!!");
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
			} else {
				System.out.println("Invalid Choice");
			}

		}

		private static void bookDetails(ResultSet result) throws SQLException {
			System.out.println("Book Id: " + result.getInt("book_id"));
			System.out.println("Book Name: " + result.getString("book_name"));
			System.out.println("Price: " + result.getDouble("price"));
			System.out.println("Author: " + result.getString("author"));
			System.out.println("Quantity: " + result.getInt("quantity"));
			System.out.println("Book Type: " + result.getString("book_type"));
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
	}



