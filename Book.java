public class Book {
	private String name;
	private String author;
	private double price;

	// 构造方法
	public Book(String name, String author, double price) {
		this.name = name;
		this.author = author;
		this.price = price;

	}
	// Getter和Setter方法

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
