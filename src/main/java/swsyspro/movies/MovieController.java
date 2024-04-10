package swsyspro.movies;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    /** @param request JSON object with field {@code name} of type string */
    @PostMapping("/movies")
    public Movie postMovie (@RequestBody Map<String, String> request, HttpServletResponse response)
    {
        String name = request.get("name");
        long id = next_id.getAndIncrement();
        Movie movie = new Movie(id, name);
        this.movies.put(id, movie);

        System.out.printf("got movie name \"%s\", returning movie %s\n", name, movie);

        response.setStatus(201);
        return movie;
    }

    @GetMapping("/movies/id/{id}")
    public Movie getMovie (@PathVariable long id)
    {
        Movie movie = movies.get(id);
        if (movie == null)
        {
            System.out.printf("requested id %d (which does not exist), returning 404\n", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no known movie has id " + id);
        }
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
