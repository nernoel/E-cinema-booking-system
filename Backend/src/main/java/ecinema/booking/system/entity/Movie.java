package ecinema.booking.system.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String genre;

    private int runtime;

    private String description;

    private String moviePosterLink;

    private String releaseDate;

    private double rating; 

    private String category;

    private String trailer;

    private String director;

    private String producer;

    private String synopsis;

    private String mpaaFilmRatingcode;

    private List<String> castMembers;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Showtime> showtimes;

    // No-Args Constructor
    public Movie() {
    }

    // All-Args Constructor
    public Movie(double rating, long id, String title, String genre, int runtime, String description, String moviePosterLink, 
                 String releaseDate, String category, String trailer, String director, String producer, 
                 String synopsis, String mpaaFilmRatingcode, List<String> castMembers, List<Showtime> showtimes) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
        this.description = description;
        this.moviePosterLink = moviePosterLink;
        this.releaseDate = releaseDate;
        this.category = category;
        this.trailer = trailer;
        this.director = director;
        this.producer = producer;
        this.synopsis = synopsis;
        this.mpaaFilmRatingcode = mpaaFilmRatingcode;
        this.castMembers = castMembers;
        this.showtimes = showtimes;
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoviePosterLink() {
        return moviePosterLink;
    }

    public void setMoviePosterLink(String moviePosterLink) {
        this.moviePosterLink = moviePosterLink;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMpaaFilmRatingcode() {
        return mpaaFilmRatingcode;
    }

    public void setMpaaFilmRatingcode(String mpaaFilmRatingcode) {
        this.mpaaFilmRatingcode = mpaaFilmRatingcode;
    }

    public List<String> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<String> castMembers) {
        this.castMembers = castMembers;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}