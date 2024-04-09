package swsyspro.movies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController
{
    @PostMapping("/movies/{name}")
    public long postMovie (@PathVariable String name)
    {
        System.out.println("got movie: " + name);
        return 0;
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie (@PathVariable int id)
    {
        System.out.println("requested id: " + id);
        return new Movie(0, "uhhhh");
    }
}
