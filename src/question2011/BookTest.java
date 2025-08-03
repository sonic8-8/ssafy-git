package question2011;

public class BookTest {
    public static void main(String[] args) {

        Book book1 = new Book("21424", "Java Pro", "김하나", "jean.kr", 15000, "Java 기본 문법");
        Book book2 = new Book("21425", "Java Pro2", "김하나", "jean.kr", 25000, "Java 응용");
        Book book3 = new Book("35355", "분석설계", "소나무", "jean.kr", 30000, "SW 모델링");
        Book book4 = new Magazine("45678", "월간 알고리즘", "홍길동", "jean.kr", 10000, "1월 알고리즘", 2021, 1);

        BookManager manager = new BookManager(4);
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);

        System.out.println("*******도서 전체 목록*******");
        Book[] books = manager.getList();
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("*******일반 도서 목록*******");
        books = manager.getBooks();
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("*******잡지 목록*******");
        Magazine[] magazines = manager.getMagazines();
        for (Magazine magazine : magazines) {
            System.out.println(magazine);
        }

        System.out.println("*******도서 제목 포함검색: Java*******");
        books = manager.searchByTitle("Java");
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.print("도서 가격 총합 : " + manager.getTotalPrice());
        System.out.println();
        System.out.print("도서 가격 평균 : " + manager.getPriceAvg());
    }
}
