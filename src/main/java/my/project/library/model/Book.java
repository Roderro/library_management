package my.project.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Название не должна быть пустым")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "[а-яА-Я]{2,} [а-яА-Я]{2,} [а-яА-Я]{2,}", message = "ФИО должна вводится в формате 'Иванов Иван Иванович'")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Дата публикации не должна быть пустой")
    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @ManyToOne()
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Reader owner;


    private LocalDateTime dayOfCapture;

    @Transient
    private boolean isOverdue = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    public LocalDateTime getDayOfCapture() {
        return dayOfCapture;
    }

    public void setDayOfCapture(LocalDateTime dayOfCapture) {
        this.dayOfCapture = dayOfCapture;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }


}
