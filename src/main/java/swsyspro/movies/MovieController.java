package swsyspro.movies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// TODO set up docker compose

@RestController
public class MovieController
{
    /** in-memory database for movies */
    private final Map<Long, Movie> movies = Collections.synchronizedSortedMap(new TreeMap<>());
    /** id the next posted movie will receive */
    private final AtomicLong next_id = new AtomicLong();


    // TODO accept json movie (without id?) as post request body instead of path variable
    @PostMapping("/movies/{name}")
    public long postMovie (@PathVariable String name)
    {
        long id = next_id.getAndIncrement();
        this.movies.put(id, new Movie(id, name));
        System.out.printf("got movie \"%s\", returning id %d\n", name, id);
        return id;
        // TODO return whole movie, not just id
        // TODO return code 201
    }

    // TODO refactor to /movies/id/{id}
    @GetMapping("/movies/{id}")
    public Movie getMovie (@PathVariable long id)
    {
        // TODO return error message with code 404 if id doesn't exist
        Movie movie = movies.get(id);
        System.out.printf("requested id %d, returning %s\n", id, movie);
        return movie;
    }

    @GetMapping("/movies")
    public Collection<Movie> getMovies ()
    {
        System.out.println("requested all movies");
        return this.movies.values();
    }

    // TODO GET /movies/name/{name} endpoint
    // TODO DELETE /movies/id/{id} endpoint
}
