package dao;

import java.sql.*;
import java.util.ArrayList;
import dto.Book;

public class BookRepository {
    private static BookRepository instance = new BookRepository();

    public static BookRepository getInstance() {
        return instance;
    }

    private String jdbcUrl = "jdbc:mysql://localhost:3306/bookmarketdb?useSSL=false&serverTimezone=UTC";
    private String jdbcUser = "root";
    private String jdbcPassword = "1234";

    // 전체 도서 리스트 조회
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> listOfBooks = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(rs.getString("b_id"), rs.getString("b_name"), rs.getInt("b_unitPrice"));
                book.setAuthor(rs.getString("b_author"));
                book.setDescription(rs.getString("b_description"));
                book.setPublisher(rs.getString("b_publisher"));
                book.setCategory(rs.getString("b_category"));
                book.setUnitsInStock(rs.getLong("b_unitsInStock"));
                book.setReleaseDate(rs.getString("b_releaseDate"));
                book.setCondition(rs.getString("b_condition"));
                book.setFileName(rs.getString("b_fileName"));
                listOfBooks.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfBooks;
    }

    // 도서 ID로 도서 조회
    public Book getBookById(String bookId) {
        Book book = null;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE b_id = ?")) {

            pstmt.setString(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(rs.getString("b_id"), rs.getString("b_name"), rs.getInt("b_unitPrice"));
                book.setAuthor(rs.getString("b_author"));
                book.setDescription(rs.getString("b_description"));
                book.setPublisher(rs.getString("b_publisher"));
                book.setCategory(rs.getString("b_category"));
                book.setUnitsInStock(rs.getLong("b_unitsInStock"));
                book.setReleaseDate(rs.getString("b_releaseDate"));
                book.setCondition(rs.getString("b_condition"));
                book.setFileName(rs.getString("b_fileName"));
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    // 도서 추가
    public void addBook(Book book) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, book.getBookId());
            pstmt.setString(2, book.getName());
            pstmt.setInt(3, book.getUnitPrice());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getDescription());
            pstmt.setString(6, book.getPublisher());
            pstmt.setString(7, book.getCategory());
            pstmt.setLong(8, book.getUnitsInStock());
            pstmt.setString(9, book.getReleaseDate());
            pstmt.setString(10, book.getCondition());
            pstmt.setString(11, book.getFileName());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
