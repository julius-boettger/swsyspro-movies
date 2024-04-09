package swsyspro.movies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController
{
    @GetMapping("/movie")
    public Movie getMovie ()
    {
        return new Movie(0, "test");
    }
}
